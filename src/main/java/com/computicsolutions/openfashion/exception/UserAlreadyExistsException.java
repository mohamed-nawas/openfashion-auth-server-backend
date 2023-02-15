package com.computicsolutions.openfashion.exception;

/**
 * User already exists exception
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * User already exists exception with error msg
     *
     * @param message message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * User already exists exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public UserAlreadyExistsException(String message, Throwable error) {
        super(message, error);
    }
}
