package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.dto.request.UserRegistrationRequestDto;
import com.computicsolutions.openfashion.entity.Role;
import com.computicsolutions.openfashion.entity.User;
import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.enums.RoleType;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.exception.UserAlreadyExistsException;
import com.computicsolutions.openfashion.exception.UserRoleNotFoundException;
import com.computicsolutions.openfashion.service.RoleService;
import com.computicsolutions.openfashion.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests {@link UserController} class
 */
class UserControllerTest {

    private static final String USER_NAME = "lionelmessi";
    private static final String EMAIL = "lio10@hotmail.com";
    private static final String PASSWORD = "123456789";
    private static final RoleType ROLE_TYPE_USER = RoleType.USER;
    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String ERROR_STATUS = "ERROR";
    private static final String SUCCESS_MESSAGE = "Successfully returned the data.";
    private static final String ERROR_MESSAGE = "Oops!! Something went wrong. Please try again.";
    private static final String FAILED = "failed";
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    private static final String CREATE_USER_URI = "/api/v1/auth/users/register";

    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);
        UserController userController = new UserController(userService, roleService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    /**
     * Start of tests for createUser method
     * Api context: /api/v1/auth/users/register
     */
    @Test
    void Should_ReturnOk_When_CreatingUserIsSuccessful() throws Exception {
        when(roleService.getRoleByName(anyString())).thenReturn(new Role());
        doNothing().when(userService).createUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_USER_URI)
                        .content(getSampleUserRegistrationRequestDto().toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value(SUCCESS_STATUS))
                .andExpect(jsonPath("$.message").value(SuccessResponseStatusType.CREATE_USER.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(SuccessResponseStatusType.CREATE_USER.getCode()))
                .andExpect(jsonPath("$.data.userName").value(USER_NAME))
                .andExpect(jsonPath("$.displayMessage").value(SUCCESS_MESSAGE));
    }

    @Test
    void Should_ReturnBadRequest_When_CreatingUserForMissingRequiredFields() throws Exception {
        UserRegistrationRequestDto dto = getSampleUserRegistrationRequestDto();
        dto.setRoleType(null);

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_USER_URI)
                        .content(dto.toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value(ERROR_STATUS))
                .andExpect(jsonPath("$.message").value(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS
                        .getMessage()))
                .andExpect(jsonPath("$.errorCode").value(ErrorResponseStatusType.MISSING_REQUIRED_FIELDS
                        .getCode()))
                .andExpect(jsonPath("$.displayMessage").value(ERROR_MESSAGE));
    }

    @Test
    void Should_ReturnBadRequest_When_CreatingUserForRoleNotFound() throws Exception {
        when(roleService.getRoleByName(anyString())).thenThrow(new UserRoleNotFoundException(FAILED));

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_USER_URI)
                        .content(getSampleUserRegistrationRequestDto().toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value(ERROR_STATUS))
                .andExpect(jsonPath("$.message").value(ErrorResponseStatusType.ROLE_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$.errorCode").value(ErrorResponseStatusType.ROLE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.displayMessage").value(ERROR_MESSAGE));
    }

    @Test
    void Should_ReturnBadRequest_When_CreatingUserForUserAlreadyExists() throws Exception {
        when(roleService.getRoleByName(anyString())).thenReturn(new Role());
        doThrow(new UserAlreadyExistsException(FAILED)).when(userService).createUser(any(User.class));

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_USER_URI)
                        .content(getSampleUserRegistrationRequestDto().toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value(ERROR_STATUS))
                .andExpect(jsonPath("$.message").value(ErrorResponseStatusType.USER_ALREADY_EXISTS.getMessage()))
                .andExpect(jsonPath("$.errorCode").value(ErrorResponseStatusType.USER_ALREADY_EXISTS.getCode()))
                .andExpect(jsonPath("$.displayMessage").value(ERROR_MESSAGE));
    }

    @Test
    void Should_ReturnInternalServerError_When_CreatingUserIsFailed() throws Exception {
        when(roleService.getRoleByName(anyString())).thenThrow(new OpenFashionAuthException(FAILED));

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_USER_URI)
                        .content(getSampleUserRegistrationRequestDto().toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value(ERROR_STATUS))
                .andExpect(jsonPath("$.message").value(ErrorResponseStatusType.INTERNAL_SERVER_ERROR
                        .getMessage()))
                .andExpect(jsonPath("$.errorCode").value(ErrorResponseStatusType.INTERNAL_SERVER_ERROR
                        .getCode()))
                .andExpect(jsonPath("$.displayMessage").value(ERROR_MESSAGE));
    }

    /**
     * This method returns a sample UserRegistrationRequestDto
     *
     * @return UserRegistrationRequestDto
     */
    private UserRegistrationRequestDto getSampleUserRegistrationRequestDto() {
        UserRegistrationRequestDto dto = new UserRegistrationRequestDto();
        dto.setUsername(USER_NAME);
        dto.setEmail(EMAIL);
        dto.setPassword(PASSWORD);
        dto.setRoleType(ROLE_TYPE_USER);
        return dto;
    }
}