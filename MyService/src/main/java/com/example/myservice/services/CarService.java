package com.example.myservice.services;

import com.example.myservice.data.CarRepository;
import com.example.myservice.entities.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public void addCar(Car car) {
        carRepository.save(car);
    }

    public Car getCar(String plateNumber) {
        return carRepository.findByPlateNumber(plateNumber).orElse(null);
    }

    public List<Car> getCars() {
        return carRepository.findAll();
    }
}
