package com.computicsolutions.openfashion.service;

import com.computicsolutions.openfashion.entity.AuthUserDetail;
import com.computicsolutions.openfashion.entity.User;
import com.computicsolutions.openfashion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User Detail Service
 */
@Service("userDetailsService")
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByEmail(username).isPresent() ?
                userRepository.findByEmail(username) : userRepository.findByUsername(username).isPresent() ?
                userRepository.findByUsername(username) : userRepository.findById(username);

        if (!optionalUser.isPresent())
            throw new UsernameNotFoundException("Invalid username");

        return new AuthUserDetail(optionalUser.get());
    }
}
