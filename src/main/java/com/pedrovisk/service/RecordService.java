package com.pedrovisk.service;

import com.pedrovisk.exception.InsufficientBalanceException;
import com.pedrovisk.exception.RecordNotFoundException;
import com.pedrovisk.model.OperationEntity;
import com.pedrovisk.model.RecordEntity;
import com.pedrovisk.model.Status;
import com.pedrovisk.model.UserEntity;
import com.pedrovisk.model.dto.CalculatorRequest;
import com.pedrovisk.model.dto.RecordResponse;
import com.pedrovisk.repository.RecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecordService {

    private final RecordRepository recordRepository;
    private final OperationService operationService;
    private final UserService userService;
    private final CalculatorService calculatorService;


    public RecordResponse saveOperation(String userName, CalculatorRequest request) {

        log.info("Saving operation for with request: {}: ", request);
        UserEntity userEntity = userService.findByUsername(userName);
        log.debug("UserEntity found: " + userEntity);
        OperationEntity operationEntity = operationService.findOperationById(request.getOperationId());
        log.debug("OperationEntity found: {}", operationEntity);

        var cost = operationEntity.getCost() * request.getAmount();
        var currentBalance = getCurrentBalance(userEntity);

        // Check if userEntity has enough balance
        if (currentBalance - cost < 0) {
            log.error("Insufficient balance for the user. Current balance: {}, operationEntity cost: {}",
                    currentBalance, cost);
            throw new InsufficientBalanceException("Insufficient balance for this operation");
        }
        var result = calculatorService.executeCalculation(operationEntity.getType(), request);
        log.debug("Result of calculation: {}", result);

        var savedRecordEntity = saveRecordEntity(request, userEntity, operationEntity, currentBalance, cost, result);
        log.info("RecordEntity saved with id: {}", savedRecordEntity.getId());

        return new RecordResponse(savedRecordEntity);

    }

    @Transactional
    private RecordEntity saveRecordEntity(CalculatorRequest request, UserEntity userEntity,
                                          OperationEntity operationEntity, Float currentBalance, float cost,
                                          String result) {
        // Deduct the operationEntity cost and create a new RecordEntity
        RecordEntity recordEntity = new RecordEntity();
        recordEntity.setUserEntity(userEntity);
        recordEntity.setOperationEntity(operationEntity);
        recordEntity.setAmount(request.getAmount());
        recordEntity.setUserBalance(currentBalance - cost);  // Update balance after deduction
        recordEntity.setOperationResponse(result);
        recordEntity.setDate(LocalDateTime.now(ZoneId.of("UTC")));
        recordEntity.setStatus(Status.ACTIVE);
        log.debug("Saving RecordEntity with data: {}", recordEntity);
        return recordRepository.save(recordEntity);
    }


    public Float getCurrentBalance(UserEntity userEntity) {

        log.debug("Getting current balance for user with id: {}", userEntity);
        var lastRecord = recordRepository.findTopByUserEntityOrderByDateDesc(userEntity);

        return lastRecord != null ? lastRecord.getUserBalance() : 0;

    }

    public Float getCurrentBalance(String username) {

        log.debug("Getting current balance for username: {}", username);
        var userEntity = userService.findByUsername(username);
        var lastRecord = recordRepository.findTopByUserEntityOrderByDateDesc(userEntity);

        return lastRecord != null ? lastRecord.getUserBalance() : 0;

    }


    @Transactional
    public void deleteRecord(Long recordId, String username) {
        log.info("Soft-Deleting record with id: {}", recordId);

        UserEntity userEntity = userService.findByUsername(username);
        log.debug("UserEntity found: {}", userEntity);

        RecordEntity recordEntity =
                recordRepository.findById(recordId).orElseThrow(() -> new RecordNotFoundException("Record not found"));

        if (!recordEntity.getUserEntity().equals(userEntity)) {
            log.error("User is not authorized to delete this record");
            throw new RecordNotFoundException("Record not found");
        }

        log.debug("RecordEntity found: {}", recordEntity);
        recordEntity.setStatus(Status.INACTIVE);

        recordRepository.save(recordEntity);
        log.info("Record with id: {} soft-deleted successfully", recordId);
    }

    public List<RecordResponse> getAllRecordsByUser(UserEntity user) {
        log.info("Getting all active records for user with id: {}", user.getId());
        var records =  recordRepository.findAllByUserEntityAndStatus(user, Status.ACTIVE);
        log.debug("Found {} records for user with id: {}", records.size(), user.getId());
        return records.stream().map(RecordResponse::new).toList();
    }

    public List<RecordResponse> getAllRecordsByUserId(Long userId) {
        UserEntity userEntity = userService.findById(userId);
        return getAllRecordsByUser(userEntity);
    }

    public List<RecordResponse> getAllRecordsByUsername(String username) {
        UserEntity userEntity = userService.findByUsername(username);
        return getAllRecordsByUser(userEntity);
    }


}
