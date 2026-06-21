package com.example.customerservice.controllers;

import com.example.customerservice.entities.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class CustomerRestTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testAddCustomer() throws Exception {
        Customer customer = new Customer("C001", "Alice", "alice@example.com");
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomers() throws Exception {
        Customer customer = new Customer("C001", "Alice", "alice@example.com");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer("C001", "Alice", "alice@example.com");
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)));

        mockMvc.perform(get("/customers/C001"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHelloEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello from CustomerService"));
    }
}
