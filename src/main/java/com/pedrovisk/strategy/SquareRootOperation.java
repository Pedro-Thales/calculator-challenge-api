package com.pedrovisk.strategy;

import com.pedrovisk.model.dto.CalculatorRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SquareRootOperation implements OperationStrategy {
    @Override
    public String execute(CalculatorRequest request) {
        log.info("Executing square root operation");
        if (request.getFirstValue() < 0) {
            log.error("Cannot compute square root of a negative number: {}", request.getFirstValue());
            throw new ArithmeticException("Cannot compute square root of a negative number");
        }
        return String.valueOf(Math.sqrt(request.getFirstValue()));
    }
}
