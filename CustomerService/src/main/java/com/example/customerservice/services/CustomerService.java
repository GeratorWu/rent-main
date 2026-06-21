package com.example.customerservice.services;

import com.example.customerservice.data.CustomerRepository;
import com.example.customerservice.entities.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void addCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer getCustomer(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
