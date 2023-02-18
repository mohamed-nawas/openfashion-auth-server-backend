package com.computicsolutions.openfashion.dto.response;

import com.computicsolutions.openfashion.entity.Role;
import lombok.Getter;

/**
 * Role DTO for response
 */
@Getter
public class RoleResponseDto implements ResponseDto {

    private final String roleId;
    private final String roleName;

    public RoleResponseDto(Role role) {
        this.roleId = role.getId();
        this.roleName = role.getName();
    }

    @Override
    public String toLogJson() {
        return toJson();
    }
}
