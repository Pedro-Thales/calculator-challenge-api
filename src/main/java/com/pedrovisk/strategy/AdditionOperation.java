package com.pedrovisk.strategy;

import com.pedrovisk.model.dto.CalculatorRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AdditionOperation implements OperationStrategy {
    @Override
    public String execute(CalculatorRequest request) {
        log.info("Executing addition operation");
        return String.valueOf(request.getFirstValue() + request.getSecondValue());

    }

}
