package com.pedrovisk.service;

import com.pedrovisk.api.RandomApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RandomGeneratorService {

    private final RandomApi randomApi;

    public String generateRandomString() {
        return randomApi.generateRandomString();
    }
}
