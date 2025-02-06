package com.algocoding.webflux.handler;

import com.algocoding.webflux.dao.CustomerDao;
import com.algocoding.webflux.dto.Customer;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerHandler {
    private final CustomerDao dao;

    public CustomerHandler(CustomerDao dao) {
        this.dao = dao;
    }

    public Mono<ServerResponse> loadCustomers(ServerRequest request) {
        Flux<Customer> customerList = dao.getCustomerList();
        return ServerResponse.ok().body(customerList, Customer.class);
    }

    public Mono<ServerResponse> findCustomer(ServerRequest request) {
        int customerId;
        try {
            customerId = Integer.parseInt(request.pathVariable("input"));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
/*
          There are two ways to get the customer from Flux list of customers:
*/

//        Mono<Customer> customerMono = dao.getCustomerList()
//                .filter(customer -> customer.getId() == customerId)
//                .take(1)
//                .single();

        Mono<Customer> customerMono = dao.getCustomerList()
                .filter(customer -> customer.getId() == customerId)
                .next();

        return ServerResponse.ok().body(customerMono, Customer.class);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        Mono<Customer> customerMono = request.bodyToMono(Customer.class);
        Mono<String> saveResponse = customerMono.map(customer -> customer.getId() + ":" + customer.getName());
        return ServerResponse.ok().body(saveResponse, String.class);
    }
}
