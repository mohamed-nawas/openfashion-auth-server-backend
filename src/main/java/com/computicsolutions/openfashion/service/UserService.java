package com.computicsolutions.openfashion.service;

import com.computicsolutions.openfashion.entity.User;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.exception.UserAlreadyExistsException;
import com.computicsolutions.openfashion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * User Service
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This method creates a User in the database
     *
     * @param user user
     */
    public void createUser(User user) {
        try {
            if (isUserExists(user.getEmail()))
                throw new UserAlreadyExistsException("User already exists in DB");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (DataAccessException e) {
            throw new OpenFashionAuthException("Failed to save user to DB for user id: {}" + user.getId(), e);
        }
    }

    private boolean isUserExists(String email) {
        try {
            return userRepository.findByEmail(email).isPresent();
        } catch (DataAccessException e) {
            throw new OpenFashionAuthException("Failed to check for user existence in DB for email: " + email, e);
        }
    }
}
