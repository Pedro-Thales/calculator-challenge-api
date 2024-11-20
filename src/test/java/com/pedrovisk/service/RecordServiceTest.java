package com.pedrovisk.service;

import com.pedrovisk.exception.InsufficientBalanceException;
import com.pedrovisk.exception.OperationNotFoundException;
import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.model.RecordEntity;
import com.pedrovisk.model.Status;
import com.pedrovisk.model.UserEntity;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.model.dto.RecordResponse;
import com.pedrovisk.repository.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RecordServiceTest {

    private RecordService recordService;
    private UserService userService;
    private OperationService operationService;
    private RecordRepository recordRepository;
    private CalculatorService calculatorService;


    @BeforeEach
    void setUp() {
        userService = Mockito.mock(UserService.class);
        operationService = Mockito.mock(OperationService.class);
        recordRepository = Mockito.mock(RecordRepository.class);
        calculatorService = Mockito.mock(CalculatorService.class);

        recordService = new RecordService(recordRepository, operationService, userService, calculatorService);
    }

    // Happy path test case
    @Test
    void testSaveOperation_HappyPath() {
        Long userId = 1L;
        var user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        var operation = new OperationEntity();
        operation.setId(10L);
        operation.setCost(20.0f);
        operation.setType("addition");

        var recordEntity = new RecordEntity();
        recordEntity.setUserEntity(user);
        recordEntity.setOperationEntity(operation);
        recordEntity.setUserBalance(100.0f);

        var request = new CalculatorRequest(1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findByUsername("user")).thenReturn(user);
        Mockito.when(operationService.findOperationById(request.getOperationId())).thenReturn(operation);
        Mockito.when(recordRepository.findTopByUserEntityOrderByDateDesc(user)).thenReturn(recordEntity);
        RecordEntity savedRecord = new RecordEntity(1L, operation, user, 1, 80f,
                "10.0", LocalDateTime.now(), Status.ACTIVE);
        Mockito.when(recordRepository.save(Mockito.any())).thenReturn(savedRecord);
        Mockito.when(calculatorService.executeCalculation(operation.getType(), request)).thenReturn("10");

        RecordResponse result = recordService.saveOperation("user", request);

        assertNotNull(result);
        assertEquals(userId, result.userId());
        assertEquals(operation.getId(), result.operationId());
        assertEquals(80.0f, result.userBalance());
        assertEquals("10.0", result.operationResponse());
    }

    // Edge case: User not found
    @Test
    void testSaveOperation_UserNotFound() {
        Long userId = 1L;
        CalculatorRequest request = new CalculatorRequest(1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findById(userId)).thenThrow();

        assertThrows(RuntimeException.class, () -> {
            recordService.saveOperation("user", request);
        });

    }

    // Edge case: Operation not found
    @Test
    void testSaveOperation_OperationNotFound() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        CalculatorRequest request = new CalculatorRequest(1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findById(userId)).thenReturn(user);
        Mockito.when(operationService.findOperationById(request.getOperationId())).thenThrow(OperationNotFoundException.class);

        assertThrows(OperationNotFoundException.class, () -> {
            recordService.saveOperation("user", request);
        });

    }

    // Edge case: Insufficient balance
    @Test
    void testSaveOperation_InsufficientBalance() {
        Long userId = 1L;
        UserEntity user = new UserEntity();
        user.setId(userId);
        user.setUsername("user");

        OperationEntity operation = new OperationEntity();
        operation.setId(1L);
        operation.setCost(20.0f);
        operation.setType("ADD");

        CalculatorRequest request = new CalculatorRequest(1L, 1, 5.0f, 5.0f);

        Mockito.when(userService.findById(userId)).thenReturn(user);
        Mockito.when(operationService.findOperationById(request.getOperationId())).thenReturn(operation);
        Mockito.when(recordRepository.findTopByUserEntityOrderByDateDesc(user)).thenReturn(null);

        assertThrows(InsufficientBalanceException.class, () -> {
            recordService.saveOperation("user", request);
        });

    }
}
