package com.computicsolutions.openfashion.repository;

import com.computicsolutions.openfashion.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Role Repository
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

    /**
     * This method finds a role in DB by name
     *
     * @param name name
     * @return Role/ null
     */
    Optional<Role> findByName(String name);
}
