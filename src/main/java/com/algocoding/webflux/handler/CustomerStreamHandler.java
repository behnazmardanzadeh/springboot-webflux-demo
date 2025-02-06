package com.algocoding.webflux.handler;

import com.algocoding.webflux.dao.CustomerDao;
import com.algocoding.webflux.dto.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerStreamHandler {
    private final CustomerDao dao;

    public CustomerStreamHandler(CustomerDao dao) {
        this.dao = dao;
    }

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<Customer> customersStream = dao.getCustomersStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM) // emits response as stream NOT an object. So it displays response as soon as it's available. Won't wait for whole response to get ready.
                .body(customersStream, Customer.class);
    }
}
