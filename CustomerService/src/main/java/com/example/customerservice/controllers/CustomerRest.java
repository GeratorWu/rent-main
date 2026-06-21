package com.example.customerservice.controllers;

import com.example.customerservice.entities.Customer;
import com.example.customerservice.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRest {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/")
    public String sayHello() {
        return "Hello from CustomerService";
    }

    @PostMapping("/customers")
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addCustomer(customer);
    }

    @GetMapping("/customers")
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable String id) {
        return customerService.getCustomer(id);
    }
}
