package org.accolite.controller;

import org.accolite.service.GreetingService;
import org.accolite.model.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/hello")
public class GreetingController {

    @Inject
    GreetingService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/greeting/{id}")
//    public Response greetingUser(@PathParam("id") int id) {
//        User u = User.findById(id);
//        String message = "{" +
//                "\"msg\": \""+service.greeting(u.name)+
//                "\"}";
//        return Response.ok(message).build();
//    }
}