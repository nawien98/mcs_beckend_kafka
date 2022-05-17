package org.accolite.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;

@Path("/api")
public class ServiceController {
    private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/quarkus")
    public Response get(@QueryParam("g") String groupId,@QueryParam("a") String artifactId ,@QueryParam("e") String[] extensions) {
        logger.info("g: {}",groupId);
        logger.info("a: {}",artifactId);
        logger.info("e: {}", (Object) extensions);
        return Response.ok(extensions).build();
    }

    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/health")
    public Response health() {
        logger.info("Server health check");
//        return Response.ok("ok").build();
        File zipOutput = new File("/Users/boris/Projects/ms-accelerator/backend/temp/getting-started.zip");
        Response.ResponseBuilder response = Response.ok((Object) zipOutput);
        response.header("Content-Disposition", "attachment;filename=" + "getting-started.zip");
//        return Response.ok((Object) zipOutput).build();
        return response.build();
    }
}
