package com.feldmann.car_service.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
