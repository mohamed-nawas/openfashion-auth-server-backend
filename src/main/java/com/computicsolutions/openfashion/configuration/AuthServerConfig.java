package com.computicsolutions.openfashion.configuration;

import com.computicsolutions.openfashion.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Authorization Server Configuration
 */
@Configuration
public class AuthServerConfig implements AuthorizationServerConfigurer {

    private static final String IS_AUTHENTICATED = "isAuthenticated()";
    private static final String PERMISSION_ALL = "permitAll()";
    private static final String BAD_CREDENTIALS = "Bad credentials";

    private final PasswordEncoder passwordEncoder;
    private final DataSource dataSource;
    private final AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    @Autowired
    public AuthServerConfig(PasswordEncoder passwordEncoder, DataSource dataSource,
                            AuthenticationManager authenticationManager, UserDetailService userDetailService) {
        this.passwordEncoder = passwordEncoder;
        this.dataSource = dataSource;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
    }

    /**
     * This method configure the security of the authorization server (i.e. /oauth endpoint)
     *
     * @param security auth server security config
     * @throws Exception exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.checkTokenAccess(IS_AUTHENTICATED).tokenKeyAccess(PERMISSION_ALL);
    }

    /**
     * This method configure the client details registered within the auth server
     *
     * @param clients registered clients
     * @throws Exception exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    /**
     * This method configure the non security features of authorization server endpoints.
     *
     * @param endpoints auth endpoints
     * @throws Exception exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager);
        endpoints.tokenStore(tokenStore());
        endpoints.userDetailsService(userDetailService);
        endpoints.tokenEnhancer(tokenEnhancer());

        endpoints.exceptionTranslator(exception -> {
            if (exception instanceof InvalidGrantException && exception.getMessage() == BAD_CREDENTIALS) {
                OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                return ResponseEntity
                        .status(oAuth2Exception.getHttpErrorCode())
                        .body(new CustomLoginException(oAuth2Exception.getMessage()));
            }

            if (exception instanceof OAuth2Exception) {
                OAuth2Exception oAuth2Exception = (OAuth2Exception) exception;
                return ResponseEntity
                        .status(oAuth2Exception.getHttpErrorCode())
                        .body(new CustomOAuthException(oAuth2Exception.getMessage()));
            }

            throw exception;
        });
    }

    /**
     * This method initializes a token store bean to store tokens in database
     *
     * @return TokenStore
     */
    @Bean
    protected TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * This method initiates a token enhancer bean with our custom token enhancer
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }
}
