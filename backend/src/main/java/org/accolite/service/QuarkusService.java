package org.accolite.service;

import org.accolite.model.Task;

import javax.enterprise.context.ApplicationScoped;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

@ApplicationScoped
public class QuarkusService {

    public boolean generateProjectService(Task task){
        boolean result = true;
        String targetPath = getPath();
        String[] commands = new String[] {
                "mvn",
                "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
                "-DprojectGroupId="+task.getGroupId(),
                "-DprojectArtifactId="+task.getArtifactId(),
                "-Dextensions="+task.getExtensions().replaceAll("\\s+", ""),
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
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getPath(){
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
}
