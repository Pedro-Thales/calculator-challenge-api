package com.pedrovisk.strategy;

import com.pedrovisk.model.dto.CalculatorRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DivisionOperation implements OperationStrategy {
    @Override
    public String execute(CalculatorRequest request) {
        log.info("Executing division operation");
        if (request.getSecondValue() == 0) {
            log.error("Division by zero is not allowed");
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return String.valueOf(request.getFirstValue() / request.getSecondValue());
    }
}
