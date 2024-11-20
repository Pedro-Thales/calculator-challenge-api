package com.pedrovisk.model.dto;

import com.pedrovisk.model.RecordEntity;

import java.time.LocalDateTime;


public record RecordResponse(Long id, Long operationId, Long userId, Float userBalance, String operationResponse,
                             LocalDateTime date, String status) {

    public RecordResponse(RecordEntity recordEntity) {
        this(recordEntity.getId(), recordEntity.getOperationEntity().getId(), recordEntity.getUserEntity().getId(),
                recordEntity.getUserBalance(), recordEntity.getOperationResponse(), recordEntity.getDate(),
                recordEntity.getStatus().getValue());
    }

}
