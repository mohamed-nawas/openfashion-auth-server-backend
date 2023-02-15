package com.computicsolutions.openfashion.exception;

/**
 * OpenFashion auth service exception
 */
public class OpenFashionAuthException extends RuntimeException {

    /**
     * OpenFashion auth service exception with error msg
     *
     * @param message message
     */
    public OpenFashionAuthException(String message) {
        super(message);
    }

    /**
     * OpenFashion auth service exception with error msg and throwable error
     *
     * @param message message
     * @param error   error
     */
    public OpenFashionAuthException(String message, Throwable error) {
        super(message, error);
    }
}
