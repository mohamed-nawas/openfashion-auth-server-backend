package com.computicsolutions.openfashion.service;

import com.computicsolutions.openfashion.entity.Role;
import com.computicsolutions.openfashion.exception.AdminAlreadyExistsException;
import com.computicsolutions.openfashion.exception.OpenFashionAuthException;
import com.computicsolutions.openfashion.exception.UserRoleNotFoundException;
import com.computicsolutions.openfashion.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Role Service
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * This method creates a Role in the database
     *
     * @param role role
     */
    public void createRole(Role role) {
        try {
            if (role.getName().equals("ADMIN") && isAdminExists())
                throw new AdminAlreadyExistsException("Admin already exists in the DB");
            roleRepository.save(role);
        } catch (DataAccessException e) {
            throw new OpenFashionAuthException("Failed to save role to DB for role id: " + role.getId(), e);
        }
    }

    public Role getRoleByName(String name) {
        try {
            Optional<Role> optionalRole = roleRepository.findByName(name);
            if (!optionalRole.isPresent())
                throw new UserRoleNotFoundException("User role is not found in DB");
            return optionalRole.get();
        } catch (DataAccessException e) {
            throw new OpenFashionAuthException("Failed to get role from DB by name for name: " + name, e);
        }
    }

    /**
     * This method finds if an ADMIN already exists in the system
     *
     * @return true/ false
     */
    private boolean isAdminExists() {
        try {
            return roleRepository.findByName("ADMIN").isPresent();
        } catch (DataAccessException e) {
            throw new OpenFashionAuthException("Failed to get admin user from DB", e);
        }
    }
}
