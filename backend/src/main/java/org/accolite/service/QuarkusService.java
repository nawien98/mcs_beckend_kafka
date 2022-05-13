package org.accolite.service;

import org.accolite.controller.GenerateController;
import org.accolite.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@ApplicationScoped
public class QuarkusService {
    private static final Logger logger = LoggerFactory.getLogger(QuarkusService.class);
    public boolean generateProjectService(Task task){
        boolean result = true;
        String targetPath = getPath();
        logger.info("[generateProjectService] request: {}",task);
        String[] commands = new String[] {
                "mvn",
                "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
                "-DprojectGroupId="+task.getGroupId(),
                "-DprojectArtifactId="+task.getArtifactId(),
                "-Dextensions="+task.getExtensions().replace("\\s+", ""),
        };

        try {
            Process process = Runtime.getRuntime().exec(commands, null, new File(targetPath));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("BUILD FAILURE")){
                    result = false;
                }
            }
            if (result){
                result = mkdirMVC(targetPath,task);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String getPath(){
        String command = "pwd";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            if ((line = reader.readLine()) != null){
                return line +"/temp";
            }
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean mkdirMVC(String path, Task task) {
        String[] commands = new String[] {
                "mkdir", "controller" ,"model", "service"
        };
        String targetPath = path+"/"+task.getArtifactId()+"/src/main/java/"+task.getGroupId().replace(".","/");
        logger.info("[generateProjectService - mkdirMVC] mkdir path: {}",targetPath);
        try {
        Runtime.getRuntime().exec(commands, null, new File(targetPath));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
