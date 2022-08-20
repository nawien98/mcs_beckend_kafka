package org.accolite.controller;

import org.accolite.interceptor.Logged;
import org.accolite.service.GreetingService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingController {

    @Inject
    GreetingService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Logged
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

}