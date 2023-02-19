package com.computicsolutions.openfashion.configuration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Custom Login Exception
 */
@JsonSerialize(using = CustomLoginExceptionSerializer.class)
public class CustomLoginException extends OAuth2Exception {

    public CustomLoginException(String msg) {
        super(msg);
    }
}
