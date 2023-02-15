package com.computicsolutions.openfashion.exception;

/**
 * Admin already exists exception
 */
public class AdminAlreadyExistsException extends RuntimeException {

    /**
     * Admin already exists exception with error msg
     *
     * @param message message
     */
    public AdminAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Admin already exists exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public AdminAlreadyExistsException(String message, Throwable error) {
        super(message, error);
    }
}
