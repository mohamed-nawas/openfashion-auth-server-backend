package com.computicsolutions.openfashion.enums;

/**
 * Enum values for role type
 */
public enum RoleType {

    ADMIN("ADMIN"),
    USER("USER");

    private final String type;

    RoleType(String type) {
        this.type = type;
    }
}
