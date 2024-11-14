package com.pedrovisk.model.dto;

import com.pedrovisk.model.Status;
import com.pedrovisk.model.User;

public record UserResponse(Long id, String username, String password, Status status) {
    public UserResponse(User user) {
        this(user.getId(), user.getUsername(), user.getPassword(), user.getStatus());
    }
}
