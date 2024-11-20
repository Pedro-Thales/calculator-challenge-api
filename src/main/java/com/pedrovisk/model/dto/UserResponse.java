package com.pedrovisk.model.dto;

import com.pedrovisk.model.Status;
import com.pedrovisk.model.UserEntity;

public record UserResponse(Long id, String username, String password, Status status) {
    public UserResponse(UserEntity userEntity) {
        this(userEntity.getId(), userEntity.getUsername(), userEntity.getPassword(), userEntity.getStatus());
    }
}
