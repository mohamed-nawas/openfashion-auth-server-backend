package com.computicsolutions.openfashion.dto.request;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for Role registration request
 */
@Getter
@Setter
public class RoleRegistrationRequestDto implements RequestDto {

    private String roleName;

    @Override
    public String toLogJson() {
        return toJson();
    }

    @Override
    public boolean isRequiredAvailable() {
        return isNonEmpty(roleName);
    }

    @Override
    public boolean isNonEmpty(String field) {
        return field != null && !field.trim().isEmpty();
    }
}
