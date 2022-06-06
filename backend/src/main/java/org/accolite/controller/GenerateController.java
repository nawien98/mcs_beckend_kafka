package org.accolite.controller;

import org.accolite.model.Task;
import org.accolite.service.QuarkusService;
import org.accolite.service.SpringBootService;
import org.jboss.resteasy.reactive.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Path("/api")
public class GenerateController {
    private static final Logger logger = LoggerFactory.getLogger(GenerateController.class);

    @Inject
    QuarkusService quarkusGenerator;
    SpringBootService springGenerator;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Path("/v1/ms-accelerator/generate")
    public Response generate(Task task){
        logger.info("[GenerateController] Request info: {}", task);



        boolean result=false;
        if (task.getLanguage().equals("java")) {
            switch (task.getFramework()) {
                case "quarkus":
                    result = quarkusGenerator.generateQuarkusService(task);
                    break;
                case "springBoot":
                    result = springGenerator.generateSpringService(task);
                    break;
                default:
                    break;
            }
        }else if (task.getLanguage().equals("kotlin")) {

        }else{//language is .NET

        }

//        boolean result = service.generateQuarkusService(task);
        if(result){
            /**
             *  The final response should be a zip file.
             */
            File zipOutput = new File(quarkusGenerator.getPath("temp",task)+"/"+task.getArtifactId()+".zip");
//            Response.ResponseBuilder response = Response.ok((Object) zipOutput);
//            response.header("Content-Disposition", "attachment;filename=" + task.getArtifactId() + ".zip");
//            return response.build();

            /**
             *  This is a temporary response.
             */
            return Response.ok("Build success").build();
        }
        return Response.status(RestResponse.Status.BAD_REQUEST).build();
    }
}
