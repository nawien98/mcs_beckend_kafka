package org.accolite.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.*;
import java.util.Properties;

public class QuarkusService {

    public static void main(String[] args) throws IOException {
        generateProject();
    }

    public static void generateProject() throws IOException {
        String targetPath = getPath();
        String[] commands = new String[] {
                "mvn",
                "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
                "-DprojectGroupId=org.acme",
                "-DprojectArtifactId=getting-started",
                "-Dextensions=resteasy-reactive,quarkus-jdbc-mysql",
        };

        try {

            Process process = Runtime.getRuntime().exec(commands, null, new File(targetPath));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
