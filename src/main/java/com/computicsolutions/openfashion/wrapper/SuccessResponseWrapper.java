package com.computicsolutions.openfashion.wrapper;

import com.computicsolutions.openfashion.dto.response.ResponseDto;
import com.computicsolutions.openfashion.enums.ResponseStatusType;
import lombok.Getter;

/**
 * Success response wrapper
 */
@Getter
public class SuccessResponseWrapper extends ResponseWrapper {
    private final int statusCode;

    /**
     * @param status         status
     * @param message        dev message
     * @param data           data
     * @param displayMessage display message
     * @param statusCode     status code
     */
    public SuccessResponseWrapper(ResponseStatusType status, String message, ResponseDto data, String displayMessage,
                                  int statusCode) {
        super(status, message, data, displayMessage);
        this.statusCode = statusCode;
    }
}
