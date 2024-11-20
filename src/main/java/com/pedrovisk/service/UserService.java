package com.pedrovisk.service;

import com.pedrovisk.exception.UserNotFoundException;
import com.pedrovisk.model.Status;
import com.pedrovisk.model.UserEntity;
import com.pedrovisk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    public UserEntity findById(Long userId) {
        log.info("Finding user by id {}", userId);
        return userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("User with id " + userId + " not found"));
    }

    public UserEntity findByUsername(String userName) {
        log.info("Finding user by username {}", userName);
        return userRepository.findByUsername(userName).orElseThrow(() ->
                new UsernameNotFoundException("User with username " + userName + " not found"));
    }

    public UserEntity findByUsernameAndIsActive(String userName) {
        log.info("Finding active user by username {}", userName);
        return userRepository.findByUsername(userName)
                .filter(userEntity -> userEntity.getStatus() == Status.ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("User with username " + userName + " not found"));
    }


}
