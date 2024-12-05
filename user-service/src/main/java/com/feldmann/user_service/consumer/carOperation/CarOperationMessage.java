package com.feldmann.user_service.consumer.carOperation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CarOperationMessage {
    private String operation;
    private Long carId;
    private Long userId;

    @JsonCreator
    public CarOperationMessage(@JsonProperty("operation") String operation,
            @JsonProperty("carId") Long carId,
            @JsonProperty("userId") Long userId) {
        this.operation = operation;
        this.carId = carId;
        this.userId = userId;
    }

}
