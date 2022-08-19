package org.accolite.controller;

import org.accolite.interceptor.Logged;
import org.accolite.service.GreetingService;
import org.accolite.model.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Path("/hello")
@RestController
public class GreetingController {

    @Inject
    GreetingService service;

    @Autowired
    ProducerTemplate producerTemplate;

    @RequestMapping(value = "/camel")
    public void startCamel() {
        producerTemplate.sendBody("direct:firstRoute", "Calling via Rest Controller");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Logged
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }


}