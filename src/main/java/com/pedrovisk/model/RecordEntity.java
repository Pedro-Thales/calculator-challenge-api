package com.pedrovisk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "records")
public class RecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "operationId", nullable = false)
    private OperationEntity operationEntity;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    private Integer amount;
    private Float userBalance;
    private String operationResponse;

    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


}
