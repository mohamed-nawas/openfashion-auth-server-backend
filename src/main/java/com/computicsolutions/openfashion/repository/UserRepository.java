package com.computicsolutions.openfashion.repository;

import com.computicsolutions.openfashion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User Repository
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * This method finds a user by username
     *
     * @param userName username
     * @return User/ null
     */
    Optional<User> findByUsername(String userName);

    /**
     * This method finds a user by email
     *
     * @param email email
     * @return User/ null
     */
    Optional<User> findByEmail(String email);

    /**
     * This method checks if the user exists in DB by username
     *
     * @param userName username
     * @return true/ false
     */
    Boolean existsByUsername(String userName);

    /**
     * This method checks if the user exists in DB by email
     *
     * @param email email
     * @return true/ false
     */
    Boolean existsByEmail(String email);
}
