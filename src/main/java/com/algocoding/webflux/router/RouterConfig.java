package com.algocoding.webflux.router;

import com.algocoding.webflux.handler.CustomerHandler;
import com.algocoding.webflux.handler.CustomerStreamHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {
    private final CustomerHandler customerHandler;
    private final CustomerStreamHandler customerStreamHandler;

    public RouterConfig(CustomerHandler customerHandler,
                        CustomerStreamHandler customerStreamHandler){
        this.customerHandler = customerHandler;
        this.customerStreamHandler = customerStreamHandler;
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction(){
        return RouterFunctions.route()
                .GET("/router/customers", customerHandler::loadCustomers) //This is Functional Endpoint
                .GET("/router/customers/stream", customerStreamHandler::getCustomers) //This is Asynchronous and nonblocking with help of stream
                .GET("/router/customers/{input}", customerHandler::findCustomer)
                .POST("/router/customers/save", customerHandler::saveCustomer)
                .build();
    }
}
