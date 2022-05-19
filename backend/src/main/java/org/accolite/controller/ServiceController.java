package org.accolite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api")
public class ServiceController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/health")
    public Response health() {
        logger.info("Server health check");
        return Response.ok("ok").build();
    }
}
