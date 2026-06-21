package com.example.myservice.data;

import com.example.myservice.entities.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {

    private final List<Car> cars = new ArrayList<>();

    public void save(Car car) {
        cars.add(car);
    }

    public Optional<Car> findByPlateNumber(String plateNumber) {
        return cars.stream()
                .filter(car -> car.getPlateNumber().equals(plateNumber))
                .findFirst();
    }

    public List<Car> findAll() {
        return new ArrayList<>(cars);
    }
}
