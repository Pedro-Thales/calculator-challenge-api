package com.pedrovisk.service;


import com.pedrovisk.exception.OperationNotFoundException;
import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OperationService {

    private final OperationRepository operationRepository;

    public OperationEntity findOperationById(Long operationId) {
        log.info("Finding operation with id {}", operationId);
        return operationRepository.findById(operationId)
                .orElseThrow(() -> new OperationNotFoundException("Operation with id " + operationId + " not found"));
    }

    public List<OperationEntity> getAllOperations() {
        log.info("Getting all operations");
        return operationRepository.findAll();
    }

}
