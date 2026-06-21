package com.example.myservice.data;

import com.example.myservice.entities.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    public void testSaveAndFindAll() {
        carRepository.save(new Car("ABC123", "Toyota", 15000.0));
        carRepository.save(new Car("XYZ789", "Honda", 18000.0));

        List<Car> cars = carRepository.findAll();
        assertEquals(2, cars.size());
    }

    @Test
    public void testFindByPlateNumber() {
        carRepository.save(new Car("ABC123", "Toyota", 15000.0));

        Optional<Car> result = carRepository.findByPlateNumber("ABC123");
        assertTrue(result.isPresent());
        assertEquals("Toyota", result.get().getBrand());
    }

    @Test
    public void testFindByPlateNumberNotFound() {
        Optional<Car> result = carRepository.findByPlateNumber("NOTFOUND");
        assertTrue(result.isEmpty());
    }
}
