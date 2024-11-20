package com.pedrovisk.strategy;

import com.pedrovisk.model.dto.CalculatorRequest;

public interface OperationStrategy {
    String execute(CalculatorRequest request);
}
