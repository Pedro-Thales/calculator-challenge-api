package com.pedrovisk.service;

import com.pedrovisk.exception.OperationNotFoundException;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CalculatorService {
    private final Map<String, OperationStrategy> operationMap;
    private final RandomGeneratorService randomGeneratorService;

    public CalculatorService(RandomGeneratorService randomGeneratorService) {
        this.randomGeneratorService = randomGeneratorService;

        operationMap = new HashMap<>();
        operationMap.put("addition", new AdditionOperation());
        operationMap.put("subtraction", new SubtractionOperation());
        operationMap.put("multiplication", new MultiplicationOperation());
        operationMap.put("division", new DivisionOperation());
        operationMap.put("square_root", new SquareRootOperation());
        operationMap.put("random_string", new RandomStringOperation(randomGeneratorService));
    }


    public String executeCalculation(String operationType, CalculatorRequest request) {
        log.debug("Executing operation: {} with request: {}", operationType, request);
        OperationStrategy operation = operationMap.get(operationType);
        if (operation == null) {
            throw new OperationNotFoundException("Unsupported operation type: " + operationType);
        }
        return operation.execute(request);
    }
}
