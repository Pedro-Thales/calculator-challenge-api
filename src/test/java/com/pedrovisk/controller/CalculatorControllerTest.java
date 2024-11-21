package com.pedrovisk.controller;

import com.pedrovisk.exception.InsufficientBalanceException;
import com.pedrovisk.exception.OperationNotFoundException;
import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.model.dto.RecordResponse;
import com.pedrovisk.service.OperationService;
import com.pedrovisk.service.RecordService;
import org.junit.jupiter.api.Test;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CalculatorControllerTest {

    @Test
    void testGetUser_balance_success() {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("validUser");
        RecordService recordService = mock(RecordService.class);
        when(recordService.getCurrentBalance("validUser")).thenReturn(100.0f);
        CalculatorController controller = new CalculatorController(null, recordService);

        Float balance = controller.getUserBalance(principal);

        assertEquals(100.0f, balance);
    }

    @Test
    void testExecuteOperation_insufficient_balance() {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("userWithLowBalance");
        RecordService recordService = mock(RecordService.class);
        CalculatorRequest request = new CalculatorRequest(1L, 10, 5.0f, 3.0f);
        when(recordService.saveOperation("userWithLowBalance", request)).thenThrow(new InsufficientBalanceException(
                "Insufficient balance for this operation"));
        CalculatorController controller = new CalculatorController(null, recordService);

        assertThrows(InsufficientBalanceException.class, () -> {
            controller.executeOperation(principal, request);
        });
    }


    @Test
    void testExecuteOperation_success() {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("validUser");
        RecordService recordService = mock(RecordService.class);
        CalculatorRequest calculatorRequest = new CalculatorRequest(1L, 2, 5.0f, 3.0f);
        RecordResponse recordEntity = new RecordResponse(1L, "addition", "user", 100f, "8.0", LocalDateTime.now(), "active");
        when(recordService.saveOperation("validUser", calculatorRequest)).thenReturn(recordEntity);
        CalculatorController controller = new CalculatorController(null, recordService);

        String result = controller.executeOperation(principal, calculatorRequest).operationResponse();

        assertEquals("8.0", result);
    }

    @Test
    void testExecuteOperation_non_existent_operation_id() {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("validUser");
        RecordService recordService = mock(RecordService.class);
        CalculatorRequest request = new CalculatorRequest(999L, 1, 5.0f, 3.0f);
        when(recordService.saveOperation("validUser", request)).thenThrow(new OperationNotFoundException("Operation " + "with id 999 not found"));
        CalculatorController controller = new CalculatorController(null, recordService);

        assertThrows(OperationNotFoundException.class, () -> {
            controller.executeOperation(principal, request);
        });
    }


    @Test
    void testGetOperations_success() {

        OperationService operationService = mock(OperationService.class);
        List<OperationEntity> mockOperations = List.of(new OperationEntity(1L, "Addition", 10.0f),
                new OperationEntity(2L, "Subtraction", 8.0f));
        when(operationService.getAllOperations()).thenReturn(mockOperations);
        CalculatorController controller = new CalculatorController(operationService, null);

        List<OperationEntity> operations = controller.getOperations();

        assertEquals(2, operations.size());
        assertEquals("Addition", operations.get(0).getType());
        assertEquals("Subtraction", operations.get(1).getType());
    }


    @Test
    void testGetRecordsByUsername_success() {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("validUser");
        RecordService recordService = mock(RecordService.class);
        List<RecordResponse> mockRecords = List.of(
                new RecordResponse(1L, "addition", "user", 100f, "8.0", LocalDateTime.now(), "active"),
                new RecordResponse(2L, "addition", "user", 100f, "8.0", LocalDateTime.now(), "active"));
        when(recordService.getAllRecordsByUsername("validUser")).thenReturn(mockRecords);
        CalculatorController controller = new CalculatorController(null, recordService);

        List<RecordResponse> records = controller.getRecordsByUsername(principal);

        assertEquals(2, records.size());
        assertEquals(mockRecords, records);
    }


    @Test
    void testExecuteOperation_with_invalid_json() {

        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("validUser");
        RecordService recordService = mock(RecordService.class);
        CalculatorController controller = new CalculatorController(null, recordService);
        String invalidJson = "{ \"operation_id\": \"invalid\", \"amount\": 5 }"; // Malformed JSON

        assertThrows(InvalidFormatException.class, () -> {
            controller.executeOperation(principal, new ObjectMapper().readValue(invalidJson, CalculatorRequest.class));
        });
    }


}