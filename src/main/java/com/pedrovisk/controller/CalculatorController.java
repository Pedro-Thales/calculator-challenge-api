package com.pedrovisk.controller;


import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.model.dto.RecordResponse;
import com.pedrovisk.service.OperationService;
import com.pedrovisk.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/calculator")
@CrossOrigin(origins = {"http://localhost", "http://localhost:3000", "https://calculator-challenge.pedrovisk.com/"}, allowedHeaders = "*")
@RequiredArgsConstructor
public class CalculatorController {

    private final OperationService operationService;
    private final RecordService recordService;


    @GetMapping("/balance")
    public Float getUserBalance(Principal principal) {
        return recordService.getCurrentBalance(principal.getName());
    }

    @PostMapping("/calculate")
    public RecordResponse executeOperation(Principal principal, @RequestBody CalculatorRequest calculatorRequest) {
        return recordService.saveOperation(principal.getName(), calculatorRequest);
    }

    @GetMapping("/operations")
    public List<OperationEntity> getOperations() {
        return operationService.getAllOperations();
    }

    @GetMapping("/records")
    public List<RecordResponse> getRecordsByUsername(Principal principal) {

        return recordService.getAllRecordsByUsername(principal.getName());
    }

    @DeleteMapping("/records/{id}")
    public ResponseEntity<String> deleteRecordById(Principal principal, @PathVariable("id") Long id) {
        recordService.deleteRecord(id, principal.getName());
        return ResponseEntity.noContent().build();
    }


}
