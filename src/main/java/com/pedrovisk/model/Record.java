package com.pedrovisk.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "operation_id", nullable = false)
    private Operation operation;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Float amount;
    //TODO think if we need to store the user balance in the record table or we move it to the user table,
    // it seems more logical to store it in the user table
    private Float userBalance;
    private String operationResponse;

    private LocalDateTime date;


}
