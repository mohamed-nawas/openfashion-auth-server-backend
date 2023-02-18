package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.dto.request.UserRegistrationRequestDto;
import com.computicsolutions.openfashion.dto.response.UserResponseDto;
import com.computicsolutions.openfashion.entity.Role;
import com.computicsolutions.openfashion.entity.User;
import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.exception.UserAlreadyExistsException;
import com.computicsolutions.openfashion.exception.UserRoleNotFoundException;
import com.computicsolutions.openfashion.service.RoleService;
import com.computicsolutions.openfashion.service.UserService;
import com.computicsolutions.openfashion.wrapper.ResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User Controller
 */
@RestController
@RequestMapping("api/v1/auth/users")
@Slf4j
public class UserController extends Controller {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * This method creates a User
     *
     * @param requestDto user registration request dto
     * @return success(user response)/ error response
     */
    @PostMapping(path = "/register", consumes = APPLICATION_JSON_UTF_8, produces = APPLICATION_JSON_UTF_8)
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserRegistrationRequestDto requestDto) {
        try {
            if (!requestDto.isRequiredAvailable()) {
                log.error("Required fields missing in user registration request DTO for creating user");
                return getBadRequestResponse(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS);
            }
            Role userRole = roleService.getRoleByName(requestDto.getRoleType().name());
            requestDto.setRole(userRole);
            User user = new User(requestDto);
            userService.createUser(user);
            UserResponseDto responseDto = new UserResponseDto(user);
//            log.debug("Created user {}", responseDto.toLogJson());
            return getSuccessResponse(SuccessResponseStatusType.CREATE_USER, responseDto);
        } catch (UserRoleNotFoundException e) {
//            log.error("User role not in DB for create user with request dto: {}", requestDto.toLogJson(), e);
            return getBadRequestResponse(ErrorResponseStatusType.ROLE_NOT_FOUND);
        } catch (UserAlreadyExistsException e) {
//            log.error("User already exists for create user with request dto: {}", requestDto.toLogJson(), e);
            return getBadRequestResponse(ErrorResponseStatusType.USER_ALREADY_EXISTS);
        } catch (OpenFashionAuthException e) {
//            log.error("Creating user was failed for requestDto: {}", requestDto.toLogJson(), e);
            return getInternalServerErrorResponse();
        }
    }
}
