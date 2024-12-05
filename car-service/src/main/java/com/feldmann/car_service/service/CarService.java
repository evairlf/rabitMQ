package com.feldmann.car_service.service;


import com.feldmann.car_service.message.CarOperationMessage;
import com.feldmann.car_service.model.Car;
import com.feldmann.car_service.repository.CarRepository;

import jakarta.transaction.Transactional;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    private final RabbitTemplate rabbitTemplate;

    public CarService(CarRepository carRepository, RabbitTemplate rabbitTemplate) {
        this.carRepository = carRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Car saveCar(Car car) {
        Car carReponse = carRepository.save(car);
        Object response = rabbitTemplate.convertSendAndReceive("user-exchange", "user.routing.key", new CarOperationMessage("save", carReponse.getId(), car.getUserId()));
        System.out.println("Mensagem recebida após enviar: " + response);
        return carReponse;
    }

    public List<Car> getCarsByUserId(Long userId) {
        return carRepository.findByUserId(userId);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public void deleteCar(Long carId) {
        Car car = carRepository.findById(carId)
        .orElseThrow(() -> new RuntimeException("Car not found!"));
        carRepository.deleteById(carId);
        sendCarOperationMessage("delete", carId, car.getUserId());
    }

    public void sendCarOperationMessage(String operation, Long carId, Long userId) {
        CarOperationMessage message = new CarOperationMessage(operation, carId, userId);
        rabbitTemplate.convertAndSend("user-exchange", "user.routing.key", message);
        System.out.println("Mensagem enviada: " + message);
    }
}
