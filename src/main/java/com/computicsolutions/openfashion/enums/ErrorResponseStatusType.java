package com.computicsolutions.openfashion.enums;

import lombok.Getter;

/**
 * Enum values for Error Response
 */
@Getter
public enum ErrorResponseStatusType {

    MISSING_REQUIRED_FIELDS(4001, "Missing required fields"),
    ROLE_NOT_FOUND(4002, "Role not found"),
    USER_ALREADY_EXISTS(4003, "User already exists, please login"),
    INVALID_LOGIN(4004, "Incorrect username or password"),
    INTERNAL_SERVER_ERROR(5000, "Internal Server Error");

    private final int code;
    private final String message;

    ErrorResponseStatusType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
