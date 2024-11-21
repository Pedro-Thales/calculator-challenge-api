package com.pedrovisk.controller;


import com.pedrovisk.infra.security.TokenService;
import com.pedrovisk.model.dto.LoginRequest;
import com.pedrovisk.model.dto.LoginResponse;
import com.pedrovisk.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        log.info("Login request received for username: {}", request.username());
        var user = userService.findByUsernameAndIsActive(request.username());
        if (passwordEncoder.matches(request.password(), user.getPassword())) {
            String token = tokenService.generateToken(user.getUsername());
            return ResponseEntity.ok(new LoginResponse(user.getUsername(), token));
        }

        return ResponseEntity.badRequest().build();

    }


}
