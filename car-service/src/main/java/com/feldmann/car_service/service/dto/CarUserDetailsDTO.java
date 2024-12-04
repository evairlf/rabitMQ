package com.feldmann.car_service.service.dto;

import com.feldmann.car_service.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarUserDetailsDTO {
    private Car car;
    private UserDTO user;
}
