package com.pedrovisk.model;


import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("active"),
    INACTIVE("inactive");

    private String value;

    Status(String value) {
        this.value = value;
    }
}
