package org.accolite.controller;
import org.accolite.model.User;
import org.accolite.service.UserService;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    UserService service;

//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("/health")
//    public Response health() {
//        logger.info("[UserController] Server health check");
//        return Response.ok("ok").build();
//    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user-service/{id}")
    public Response GerUserInfoService(@PathParam("id") long id) {
        User result = service.FindUserById(id);

        return Response.ok(result).build();
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user-service/user")
    public Response CreateUserService(User user) {
        boolean result = service.CreateUser(user);
        if (!result){
            return Response.status(RestResponse.Status.BAD_REQUEST).build();
        }
        return Response.ok(user).build();
    }

    @PUT
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user-service/{id}")
    public Response UpdateUserService(@PathParam("id") long id, User user) {
        boolean result = service.UpdateUser(id,user);
        if (!result){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.ok("{"+"\"id\": "+id+", \"name\":\""+user.name+"\"}").build();
    }


    @DELETE
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user-service/{id}")
    public Response DeleteUserService(@PathParam("id") long id) {
        boolean result = service.DeleteUserById(id);
        if (!result){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.noContent().build();
    }
}
