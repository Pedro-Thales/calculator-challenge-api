package com.pedrovisk.model.dto;

import com.pedrovisk.model.RecordEntity;

import java.time.LocalDateTime;


public record RecordResponse(Long id, String operationType, String username, Float userBalance, String operationResponse,
                             LocalDateTime date, String status) {

    public RecordResponse(RecordEntity recordEntity) {
        this(recordEntity.getId(), recordEntity.getOperationEntity().getType(), recordEntity.getUserEntity().getUsername(),
                recordEntity.getUserBalance(), recordEntity.getOperationResponse(), recordEntity.getDate(),
                recordEntity.getStatus().getValue());
    }

}
