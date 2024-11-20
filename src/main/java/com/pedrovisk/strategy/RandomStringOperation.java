package com.pedrovisk.strategy;

import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.service.RandomGeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class RandomStringOperation implements OperationStrategy {

    private final RandomGeneratorService randomGeneratorService;

    @Override
    public String execute(CalculatorRequest request) {
        log.info("Executing RandomStringOperation strategy");
        return randomGeneratorService.generateRandomString();

    }
}
