package com.pedrovisk.exception;


import org.springframework.http.HttpStatus;

public record ErrorMessage(HttpStatus status, String message) {}
