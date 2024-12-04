package com.feldmann.car_service.controller;

import com.feldmann.car_service.model.Car;
import com.feldmann.car_service.service.CarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public Car createCar(@RequestBody Car car) {
        return carService.saveCar(car);
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/user/{userId}")
    public List<Car> getCarsByUserId(@PathVariable Long userId) {
        return carService.getCarsByUserId(userId);
    }
}
