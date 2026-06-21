package com.example.customerservice.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    @Test
    public void testCustomerConstructor() {
        Customer customer = new Customer("C001", "Alice Martin", "alice@example.com");
        assertEquals("C001", customer.getId());
        assertEquals("Alice Martin", customer.getName());
        assertEquals("alice@example.com", customer.getEmail());
    }

    @Test
    public void testSetName() {
        Customer customer = new Customer("C001", "Alice Martin", "alice@example.com");
        customer.setName("Bob Dupont");
        assertEquals("Bob Dupont", customer.getName());
    }

    @Test
    public void testSetEmail() {
        Customer customer = new Customer("C001", "Alice Martin", "alice@example.com");
        customer.setEmail("bob@example.com");
        assertEquals("bob@example.com", customer.getEmail());
    }

    @Test
    public void testToString() {
        Customer customer = new Customer("C001", "Alice Martin", "alice@example.com");
        String expected = "Customer{id='C001', name='Alice Martin', email='alice@example.com'}";
        assertEquals(expected, customer.toString());
    }
}
