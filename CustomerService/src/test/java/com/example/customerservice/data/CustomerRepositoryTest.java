package com.example.customerservice.data;

import com.example.customerservice.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerRepositoryTest {

    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        customerRepository = new CustomerRepository();
    }

    @Test
    public void testSaveAndFindAll() {
        customerRepository.save(new Customer("C001", "Alice", "alice@example.com"));
        customerRepository.save(new Customer("C002", "Bob", "bob@example.com"));

        List<Customer> customers = customerRepository.findAll();
        assertEquals(2, customers.size());
    }

    @Test
    public void testFindById() {
        customerRepository.save(new Customer("C001", "Alice", "alice@example.com"));

        Optional<Customer> result = customerRepository.findById("C001");
        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Customer> result = customerRepository.findById("NOTFOUND");
        assertTrue(result.isEmpty());
    }
}
