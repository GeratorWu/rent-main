package com.example.customerservice.services;

import com.example.customerservice.data.CustomerRepository;
import com.example.customerservice.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    private CustomerService customerService;

    @BeforeEach
    public void setUp() {
        customerService = new CustomerService(new CustomerRepository());
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer("C001", "Alice", "alice@example.com");
        customerService.addCustomer(customer);
        assertEquals(1, customerService.getCustomers().size());
    }

    @Test
    public void testGetCustomers() {
        customerService.addCustomer(new Customer("C001", "Alice", "alice@example.com"));
        customerService.addCustomer(new Customer("C002", "Bob", "bob@example.com"));
        assertEquals(2, customerService.getCustomers().size());
    }

    @Test
    public void testGetCustomerById() {
        customerService.addCustomer(new Customer("C001", "Alice", "alice@example.com"));
        Customer result = customerService.getCustomer("C001");
        assertNotNull(result);
        assertEquals("Alice", result.getName());
    }

    @Test
    public void testGetCustomerNotFound() {
        Customer result = customerService.getCustomer("NOTFOUND");
        assertNull(result);
    }
}
