package com.computicsolutions.openfashion.controllers;

import com.computicsolutions.openfashion.dto.request.RoleRegistrationRequestDto;
import com.computicsolutions.openfashion.entity.Role;
import com.computicsolutions.openfashion.enums.ErrorResponseStatusType;
import com.computicsolutions.openfashion.enums.SuccessResponseStatusType;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class tests {@link RoleController} class
 */
class RoleControllerTest {

    private static final String ROLE_NAME = "USER";
    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String ERROR_STATUS = "ERROR";
    private static final String SUCCESS_MESSAGE = "Successfully returned the data.";
    private static final String ERROR_MESSAGE = "Oops!! Something went wrong. Please try again.";
    private static final String FAILED = "failed";
    private static final String APPLICATION_JSON_UTF8 = "application/json;charset=UTF-8";

    private static final String CREATE_ROLE_URI = "/api/v1/roles";

    @Mock
    private RoleService roleService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        initMocks(this);
        RoleController roleController = new RoleController(roleService);
        mockMvc = MockMvcBuilders.standaloneSetup(roleController).build();
    }

    /**
     * Start of tests for createRole method
     * Api context: /api/v1/roles
     */
    @Test
    void Should_ReturnOk_When_CreatingRoleIsSuccessful() throws Exception {
        doNothing().when(roleService).createRole(any(Role.class));

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ROLE_URI)
                        .content(getSampleRoleRegistrationRequestDto().toJson())
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.status").value(SUCCESS_STATUS))
                .andExpect(jsonPath("$.message").value(SuccessResponseStatusType.CREATE_ROLE.getMessage()))
                .andExpect(jsonPath("$.statusCode").value(SuccessResponseStatusType.CREATE_ROLE.getCode()))
                .andExpect(jsonPath("$.data.roleName").value(ROLE_NAME))
                .andExpect(jsonPath("$.displayMessage").value(SUCCESS_MESSAGE));
    }

    @Test
    void Should_ReturnBadRequest_When_CreatingRoleForMissingRequiredFields() throws Exception {
        RoleRegistrationRequestDto dto = getSampleRoleRegistrationRequestDto();
        dto.setRoleName(null);

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ROLE_URI)
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
    void Should_ReturnInternalServerError_When_CreatingRoleIsFailed() throws Exception {
        doThrow(new OpenFashionAuthException(FAILED)).when(roleService).createRole(any(Role.class));

        mockMvc.perform(MockMvcRequestBuilders.post(CREATE_ROLE_URI)
                        .content(getSampleRoleRegistrationRequestDto().toJson())
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
     * This method returns a sample RoleRegistrationRequestDto
     *
     * @return RoleRegistrationRequestDto
     */
    private RoleRegistrationRequestDto getSampleRoleRegistrationRequestDto() {
        RoleRegistrationRequestDto dto = new RoleRegistrationRequestDto();
        dto.setRoleName(ROLE_NAME);
        return dto;
    }
}