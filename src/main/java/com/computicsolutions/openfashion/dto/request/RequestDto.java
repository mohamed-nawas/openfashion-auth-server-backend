package com.computicsolutions.openfashion.dto.request;

import com.computicsolutions.openfashion.dto.BaseDto;

/**
 * Dto for Request, All RequestDto need to implement this
 */
public interface RequestDto extends BaseDto {

    /**
     * This method verifies if all required fields exists for a request
     * @return true/false
     */
    boolean isRequiredAvailable();

    /**
     * This method verifies that provided field is not empty
     * @param field field
     * @return true/false
     */
    boolean isNonEmpty(String field);
}
