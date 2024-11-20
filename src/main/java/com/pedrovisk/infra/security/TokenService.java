package com.pedrovisk.infra.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class TokenService {

    @Value("${app.security.token}")
    private String secret;

    @Value("${app.security.expiration-time-in-minutes}")
    private Integer expirationTimeInMinutes;


    public String generateToken(String username) {
        try {
            log.info("Generating token for user: " + username);
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("calculator-challenge-api")
                    .withSubject(username)
                    .withExpiresAt(generateExpirationDate())
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException e) {
            log.error("Error while trying to authenticate");
            throw new RuntimeException("Error while trying to authenticate");
        }
    }

    public String validateToken(String token) {
        try {
            log.info("Validating token");
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("calculator-challenge-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException je) {
            log.error("Invalid token");
            return null;
        }
    }


    private Instant generateExpirationDate() {
        return Instant.now().plusSeconds(60 * expirationTimeInMinutes);
    }

}
