package com.computicsolutions.openfashion.configuration;

import com.computicsolutions.openfashion.entity.Data;
import com.computicsolutions.openfashion.entity.User;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class customizes the oauth token responses with additional properties
 */
public class CustomTokenEnhancer implements TokenEnhancer {

    private static final String STATUS = "status";
    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String MESSAGE = "message";
    private static final String SUCCESS_MESSAGE = "Successfully returned the data";
    private static final String DATA = "data";
    private static final String DISPLAY_MESSAGE = "displayMessage";
    private static final String STATUS_CODE = "statusCode";

    /**
     * This method is used to customize the access token with additional information
     *
     * @param oAuth2AccessToken    access token
     * @param oAuth2Authentication authentication
     * @return access token
     */
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User user = (User) oAuth2Authentication.getPrincipal();
        Data data = new Data();
        Map<String, Object> additionalInfo = new HashMap<>();

        data.setUserId(user.getId());
        data.setUsername(user.getUsername());
        data.setEmail(user.getEmail());
        data.setRole(user.getRole().getName());

        additionalInfo.put(STATUS, SUCCESS_STATUS);
        additionalInfo.put(MESSAGE, SUCCESS_MESSAGE);
        additionalInfo.put(DATA, data);
        additionalInfo.put(DISPLAY_MESSAGE, SuccessResponseStatusType.LOGIN_USER.getMessage());
        additionalInfo.put(STATUS_CODE, SuccessResponseStatusType.LOGIN_USER.getCode());

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
