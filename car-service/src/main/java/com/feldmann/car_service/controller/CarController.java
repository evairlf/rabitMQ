package com.feldmann.car_service.controller;


import com.feldmann.car_service.model.Car;

import com.feldmann.car_service.producer.UserProducer;
import com.feldmann.car_service.service.CarService;
import com.feldmann.car_service.service.dto.CarUserDetailsDTO;
import com.feldmann.car_service.service.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    private final UserProducer userProducer;

    public CarController(CarService carService, UserProducer userProducer) {
        this.carService = carService;
        this.userProducer = userProducer;
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/user-details/{carId}")
    public CarUserDetailsDTO getCarWithUserDetails(@PathVariable Long carId) {
        Car car = carService.getAllCars().stream()
                .filter(c -> c.getId().equals(carId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Car not found!"));
        // Solicitar detalhes do usuário via RabbitMQ
        Map<String, Object> user = userProducer.requestUserDetails(car.getUserId());
        UserDTO userDTO = new UserDTO((Long) user.get("id"), (String) user.get("name"), (String) user.get("email"));
        return new CarUserDetailsDTO(car, userDTO);
    }

    @GetMapping("/user/{userId}")
    public List<Car> getCarsByUserId(@PathVariable Long userId) {
        return carService.getCarsByUserId(userId);
    }

    @DeleteMapping
    public void deleteCar(@PathVariable Long carId){
        carService.deleteCar(carId);
    }
}
