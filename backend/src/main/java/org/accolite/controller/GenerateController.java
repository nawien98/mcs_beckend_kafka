package org.accolite.controller;

import org.accolite.model.Task;
import org.accolite.model.User;
import org.accolite.service.GreetingService;
import org.accolite.service.QuarkusService;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api")
public class GenerateController {
    private static final Logger logger = LoggerFactory.getLogger(GenerateController.class);

    @Inject
    QuarkusService service;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/v1/ms-accelerator/generate")
    public Response generate(Task task){
        logger.info("[GenerateController] Request info: {}", task);
        boolean result = service.generateProjectService(task);
        if(!result){
            return Response.status(RestResponse.Status.BAD_REQUEST).entity("{"+"\"msg\": \""+"Build failure"+"\"}").build();
        }
        return Response.ok("{"+"\"msg\": \""+"Build success"+"\"}").build();
    }
}
