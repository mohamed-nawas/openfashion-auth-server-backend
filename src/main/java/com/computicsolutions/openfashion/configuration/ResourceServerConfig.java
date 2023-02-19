package com.computicsolutions.openfashion.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * Resource Server Configuration
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String REGISTRATION_ENDPOINT = "/api/v1/auth/users/register";
    private static final String ROLES_ENDPOINT = "/api/v1/roles";
    private final String RESOURCE_ID;

    @Autowired
    public ResourceServerConfig(@Value("${oauth.resource-id}") String resourceId) {
        this.RESOURCE_ID = resourceId;
    }

    /**
     * This method configure a resource id for resource server APIs
     *
     * @param resources resource
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
        resources.authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        resources.accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    /**
     * This method configure the web security of the resource server
     *
     * @param http http
     * @throws Exception exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, REGISTRATION_ENDPOINT).permitAll()
                .antMatchers(ROLES_ENDPOINT).access("hasAuthority('ADMIN')")
                .anyRequest().authenticated().and().cors().and()
                .csrf().disable();
    }
}
