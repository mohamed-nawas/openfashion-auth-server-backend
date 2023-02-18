package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.dto.request.RoleRegistrationRequestDto;
import com.computicsolutions.openfashion.dto.response.RoleResponseDto;
import com.computicsolutions.openfashion.entity.Role;
import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.service.RoleService;
import com.computicsolutions.openfashion.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role Controller
 */
@RestController
@RequestMapping("api/v1/roles")
@Slf4j
public class RoleController extends Controller {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * This method creates a Role
     *
     * @param requestDto role registration request dto
     * @return success(role response)/ error response
     */
    @PostMapping(path = "", consumes = APPLICATION_JSON_UTF_8, produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> createRole(@RequestBody RoleRegistrationRequestDto requestDto) {
        try {
            if (!requestDto.isRequiredAvailable()) {
                log.error("Required fields missing in user registration request DTO for creating user");
                return getBadRequestResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
            }
            Role role = new Role(requestDto);
            roleService.createRole(role);
            RoleResponseDto responseDto = new RoleResponseDto(role);
            log.debug("Created role {}", responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.CREATE_ROLE, responseDto);
        } catch (OpenFashionAuthException e) {
            log.error("Creating role was failed for requestDto: {}", requestDto.toLogJson(), e);
            return getInternalServerErrorResponse();
        }
    }
}
