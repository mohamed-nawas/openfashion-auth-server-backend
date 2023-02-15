package com.computicsolutions.openfashion.service;

import com.computicsolutions.openfashion.entity.User;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.exception.UserAlreadyExistsException;
import com.computicsolutions.openfashion.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * This class tests {@link UserService} class
 */
class UserServiceTest {

    private static final String USER_ID = "uid-123456789";
    private static final String USER_EMAIL = "user@yahoomail.com";
    private static final String USER_PASSWORD = "123456789";
    private static final String ERROR = "ERROR";
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userService = new UserService(userRepository, passwordEncoder);
    }

    /**
     * Start of tests for createUser method
     */
    @Test
    void Should_CreateUserInDBWithEncodedPwd_When_CreatingUserIsSuccessful() {
        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());

        userService.createUser(getSampleUser());

        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void Should_ThrowOpenFashionAuthException_When_UserExistenceCheckFailedForCreateUser() {
        User user = getSampleUser();

        when(userRepository.findByEmail(USER_EMAIL)).thenThrow(new DataAccessException(ERROR) {
        });
        OpenFashionAuthException exception = assertThrows(OpenFashionAuthException.class, () ->
                userService.createUser(user));
        assertEquals("Failed to check for user existence in DB for email: " + USER_EMAIL, exception.getMessage());
    }

    @Test
    void Should_ThrowUserAlreadyExistsException_When_UserAlreadyExistsInDBForCreateUser() {
        User user = getSampleUser();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.of(user));
        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class, () ->
                userService.createUser(user));
        assertEquals("User already exists in DB", exception.getMessage());
    }

    @Test
    void Should_ThrowOpenFashionAuthException_When_CreatingUserIsFailed() {
        User user = getSampleUser();

        when(userRepository.findByEmail(USER_EMAIL)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenThrow(new DataAccessException(ERROR) {
        });
        OpenFashionAuthException exception = assertThrows(OpenFashionAuthException.class, () ->
                userService.createUser(user));
        assertEquals("Failed to save user to DB for user id: {}" + USER_ID, exception.getMessage());
    }


    /**
     * This method returns a sample user
     *
     * @return User
     */
    private User getSampleUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setEmail(USER_EMAIL);
        user.setPassword(USER_PASSWORD);
        return user;
    }
}