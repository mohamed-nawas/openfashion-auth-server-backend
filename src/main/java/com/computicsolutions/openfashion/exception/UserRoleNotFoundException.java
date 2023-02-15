package com.computicsolutions.openfashion.exception;

/**
 * User role not found exception
 */
public class UserRoleNotFoundException extends RuntimeException {

    /**
     * User role not found exception with error msg
     *
     * @param message message
     */
    public UserRoleNotFoundException(String message) {
        super(message);
    }

    /**
     * User role not found exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public UserRoleNotFoundException(String message, Throwable error) {
        super(message, error);
    }
}
