package com.pedrovisk.service;

import com.pedrovisk.api.RandomApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
@Slf4j
public class RandomGeneratorService {

    private final RandomApi randomApi;

    public String generateRandomString() {
        log.info("Generating random string");
        return randomApi.generateRandomString();
    }
}
