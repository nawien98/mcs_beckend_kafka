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
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Path("/api")
public class GenerateController {
    private static final Logger logger = LoggerFactory.getLogger(GenerateController.class);

    @Inject
    QuarkusService service;

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/v1/ms-accelerator/generate")
    public Response generate(@QueryParam("g") String groupId,@QueryParam("a") String artifactId ,@QueryParam("e") String[] extensions) {
        Task task = new Task();
        task.setGroupId(groupId);
        task.setArtifactId(artifactId);
        task.setExtensions(extensions);
        logger.info("[GenerateController] Request info: {}", task);
        boolean result = service.generateProjectService(task);
        if (result) {
            File zipOutput = new File("/Users/boris/Projects/ms-accelerator/backend/temp/" + task.getArtifactId() + ".zip");
            Response.ResponseBuilder response = Response.ok((Object) zipOutput);
            response.header("Content-Disposition", "attachment;filename=" + task.getArtifactId() + ".zip");
            return response.build();
        }
        return Response.status(RestResponse.Status.BAD_REQUEST).build();


    }

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
////    @Produces(MediaType.APPLICATION_OCTET_STREAM)
//    @Path("/v1/ms-accelerator/generate")
//    public Response generate(Task task){
//        logger.info("[GenerateController] Request info: {}", task);
//        boolean result = service.generateProjectService(task);
//        if(!result){
////            return Response.status(RestResponse.Status.BAD_REQUEST).entity("{"+"\"msg\": \""+"Build failure"+"\"}").build();
//            File zipOutput = new File("/Users/boris/Projects/ms-accelerator/backend/temp/"+task.getArtifactId()+".zip");
//            Response.ResponseBuilder response = Response.ok((Object) zipOutput);
//            response.header("Content-Disposition", "attachment;filename=" + task.getArtifactId());
//            return response.build();
//        }
//        return Response.ok("{"+"\"msg\": \""+"Build success"+"\"}").build();
//    }
}
