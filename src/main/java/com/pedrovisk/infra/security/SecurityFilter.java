package com.pedrovisk.infra.security;

import com.pedrovisk.model.UserEntity;
import com.pedrovisk.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        var username = tokenService.validateToken(token);

        if (username != null) {
            UserEntity userEntity = userService.findByUsernameAndIsActive(username);
            var authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), null, Collections.emptyList());
            log.info("User {} authenticated successfully", username);
            authentication.setDetails(userEntity);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);


    }

    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        log.debug("Trying to recover token from Authorization header: {}", authorizationHeader);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            log.info("Token found in Authorization header");
            return authorizationHeader.substring(7);
        }
        log.error("No token found in Authorization header");
        return null;
    }

}
