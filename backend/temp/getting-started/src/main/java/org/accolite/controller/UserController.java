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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/health")
    public Response health() {
        logger.info("[UserController] Server health check");
        return Response.ok("ok").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user-service/{id}")
    public Response GerUserInfoService(@PathParam("id") int id) {
        logger.info("[UserController] get user request id: {}", id);
        User u = User.findById(id);

        return Response.ok(u).build();
    }

    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/user-service/user")
    public Response CreateUserService(User user) {
        logger.info("[UserController] create user request: {}", user);
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
        logger.info("[UserController] update user request: {} {}", id, user);
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
        logger.info("[UserController] delete user request: {}", id);
        boolean result = service.DeleteUserById(id);
        if (!result){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.noContent().build();
    }

}
