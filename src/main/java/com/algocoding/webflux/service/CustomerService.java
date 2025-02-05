package com.algocoding.webflux.service;

import com.algocoding.webflux.dao.CustomerDao;
import com.algocoding.webflux.dto.Customer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao dao;

    public CustomerService(CustomerDao dao) {
        this.dao = dao;
    }

    public List<Customer> loadAllCustomers() {
        long start = System.currentTimeMillis();
        List<Customer> customers = dao.getCustomers();
        long end = System.currentTimeMillis();
        System.out.printf("Total execution time: %d\n", end - start);
        return customers;
    }

    public Flux<Customer> loadAllCustomersStream() {
        long start = System.currentTimeMillis();
        Flux<Customer> customers = dao.getCustomersStream();
        long end = System.currentTimeMillis();
        System.out.printf("Total execution time: %d\n", end - start);
        return customers;
    }
}
