package com.computicsolutions.openfashion.service;

import com.computicsolutions.openfashion.entity.Role;
import com.computicsolutions.openfashion.exception.AdminAlreadyExistsException;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.exception.UserRoleNotFoundException;
import com.computicsolutions.openfashion.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * This class tests {@link RoleService} class
 */
class RoleServiceTest {

    private static final String ROLE_ID = "rid-123456789";
    private static final String ROLE_NAME_ADMIN = "ADMIN";
    private static final String ROLE_NAME_USER = "USER";
    private static final String ERROR = "ERROR";
    @Mock
    private RoleRepository roleRepository;
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        roleService = new RoleService(roleRepository);
    }

    /**
     * Start of tests for createRole method
     */
    @Test
    void Should_CreateRoleInDB_When_CreatingRoleIsSuccessful() {
        roleService.createRole(getSampleRole());

        verify(roleRepository).save(any(Role.class));
    }

    @Test
    void Should_ThrowOpenFashionAuthException_When_AdminCheckFailedForCreatingAdminRole() {
        Role role = getSampleRole();
        role.setName(ROLE_NAME_ADMIN);

        when(roleRepository.findByName(anyString())).thenThrow(new DataAccessException(ERROR) {
        });
        OpenFashionAuthException exception = assertThrows(OpenFashionAuthException.class, () ->
                roleService.createRole(role));
        assertEquals("Failed to get admin user from DB", exception.getMessage());
    }

    @Test
    void Should_ThrowAdminAlreadyExistsException_When_AdminAlreadyExistsForCreatingAdminRole() {
        Role role = getSampleRole();
        role.setName(ROLE_NAME_ADMIN);

        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
        AdminAlreadyExistsException exception = assertThrows(AdminAlreadyExistsException.class, () ->
                roleService.createRole(role));
        assertEquals("Admin already exists in the DB", exception.getMessage());
    }

    @Test
    void Should_ThrowOpenFashionAuthException_When_CreatingRoleIsFailed() {
        Role role = getSampleRole();

        when(roleRepository.save(any(Role.class))).thenThrow(new DataAccessException(ERROR) {
        });
        OpenFashionAuthException exception = assertThrows(OpenFashionAuthException.class, () ->
                roleService.createRole(role));
        assertEquals("Failed to save role to DB for role id: " + ROLE_ID, exception.getMessage());
    }

    /**
     * Start of tests for getRoleByName method
     */
    @Test
    void Should_ReturnRole_When_GettingRoleByNameIsSuccessful() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(getSampleRole()));

        assertEquals(ROLE_ID, roleService.getRoleByName(ROLE_NAME_ADMIN).getId());
    }

    @Test
    void Should_ThrowUserRoleNotFoundException_When_GettingRoleByNameForUserRoleNotFoundInDB() {
        when(roleRepository.findByName(anyString())).thenReturn(Optional.empty());

        UserRoleNotFoundException exception = assertThrows(UserRoleNotFoundException.class, () ->
                roleService.getRoleByName(ROLE_NAME_ADMIN));

        assertEquals("User role is not found in DB", exception.getMessage());
    }

    @Test
    void Should_ThrowOpenFashionAuthException_When_GettingRoleByNameIsFailed() {
        when(roleRepository.findByName(anyString())).thenThrow(new DataAccessException(ERROR) {
        });

        OpenFashionAuthException exception = assertThrows(OpenFashionAuthException.class, () ->
                roleService.getRoleByName(ROLE_NAME_ADMIN));

        assertEquals("Failed to get role from DB by name for name: " + ROLE_NAME_ADMIN, exception.getMessage());
    }


    /**
     * This method returns a sample role
     *
     * @return Role
     */
    private Role getSampleRole() {
        Role role = new Role();
        role.setId(ROLE_ID);
        role.setName(ROLE_NAME_USER);
        return role;
    }
}