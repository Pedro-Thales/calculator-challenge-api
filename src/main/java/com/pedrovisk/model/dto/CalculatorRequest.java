package com.pedrovisk.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalculatorRequest {
    @JsonProperty("operation_id")
    private Long operationId;
    @JsonProperty("amount")
    private Integer amount;
    @JsonProperty("first_value")
    private Float firstValue;
    @JsonProperty("second_value")
    private Float secondValue;

}
