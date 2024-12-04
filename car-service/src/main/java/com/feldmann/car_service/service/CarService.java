package com.feldmann.car_service.service;


import com.feldmann.car_service.model.Car;
import com.feldmann.car_service.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getCarsByUserId(Long userId) {
        return carRepository.findByUserId(userId);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
