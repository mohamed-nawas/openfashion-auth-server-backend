package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.dto.response.ResponseDto;
import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.enums.ResponseStatusType;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import com.computicsolutions.openfashion.wrapper.ErrorResponseWrapper;
import com.computicsolutions.openfashion.wrapper.ResponseWrapper;
import com.computicsolutions.openfashion.wrapper.SuccessResponseWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Base Controller
 */
public class Controller {

    protected static final String DEFAULT_SORT = "updated";
    protected static final String UNSORTED = "unsorted";
    protected static final int DEFAULT_PAGE = 0;
    protected static final int PAGE_MAX_SIZE = 40;
    protected static final String APPLICATION_JSON_UTF_8 = "application/json;charset=UTF-8";
    protected static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String ERROR_MESSAGE = "Oops!! Something went wrong. Please try again.";
    private static final String SUCCESS_MESSAGE = "Successfully returned the data.";

    /**
     * This method creates an empty data response for bad request scenarios
     *
     * @param status error status
     * @return bad request error response
     */
    protected ResponseEntity<ResponseWrapper> getBadRequestResponse(ErrorResponseStatusType status) {
        ResponseWrapper responseWrapper = new ErrorResponseWrapper(ResponseStatusType.ERROR, status.getMessage(),
                null, ERROR_MESSAGE, status.getCode());
        return new ResponseEntity<>(responseWrapper, HttpStatus.BAD_REQUEST);
    }

    /**
     * This method creates an empty data response for the internal server error scenarios
     *
     * @return internal server error response
     */
    protected ResponseEntity<ResponseWrapper> getInternalServerErrorResponse() {
        ResponseWrapper responseWrapper = new ErrorResponseWrapper(ResponseStatusType.ERROR, ErrorResponseStatusType
                .INTERNAL_SERVER_ERROR.getMessage(), null, ERROR_MESSAGE, ErrorResponseStatusType
                .INTERNAL_SERVER_ERROR.getCode());
        return new ResponseEntity<>(responseWrapper, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * This method creates data response for success scenarios
     *
     * @param status success status
     * @param data   response data
     * @return success response
     */
    protected ResponseEntity<ResponseWrapper> getSuccessResponse(SuccessResponseStatusType status, ResponseDto data) {
        ResponseWrapper responseWrapper = new SuccessResponseWrapper(ResponseStatusType.SUCCESS, status.getMessage(),
                data, SUCCESS_MESSAGE, status.getCode());
        return new ResponseEntity<>(responseWrapper, HttpStatus.OK);
    }
}
