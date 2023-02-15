package com.computicsolutions.openfashion.enums;

/**
 * Enum values for user type
 */
public enum UserType {

    ADMIN("ADMIN"),
    USER("USER");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
