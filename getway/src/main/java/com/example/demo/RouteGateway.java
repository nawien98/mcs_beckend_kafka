package com.example.demo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.camel.builder.RouteBuilder;


@Component
public class RouteGateway extends RouteBuilder{
    @Value("${port}")
    private int port;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .component("netty4-http")
                .bindingMode(RestBindingMode.json)
                .port(port);

        rest("/account")
                .get("/{id}")
                .to("bean:accountService?method=findById(${header.id})")
                .get("/customer/{customerId}")
                .to("bean:accountService?method=findByCustomerId(${header.customerId})")
                .get("/")
                .to("bean:accountService?method=findAll")
                .post("/").consumes("application/json").type(Account.class)
                .to("bean:accountService?method=add(${body})");
    }
}
