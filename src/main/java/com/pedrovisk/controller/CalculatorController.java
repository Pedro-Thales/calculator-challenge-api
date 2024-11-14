package com.pedrovisk.controller;


import com.pedrovisk.model.dto.UserResponse;
import com.pedrovisk.repository.UserRepository;
import com.pedrovisk.service.RandomGeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/calculator")
@RequiredArgsConstructor
public class CalculatorController {

    private final RandomGeneratorService randomGeneratorService;
    private final UserRepository userRepository;

    @GetMapping("/random")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public String getRandomString() {
        return randomGeneratorService.generateRandomString();

    }


    @GetMapping("/users")
    //TODO configure cors
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(UserResponse::new).toList();

    }

}
