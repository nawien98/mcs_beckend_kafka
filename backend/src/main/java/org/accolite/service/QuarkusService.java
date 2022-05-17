package org.accolite.service;

import org.accolite.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;

@ApplicationScoped
public class QuarkusService {
    private static final Logger logger = LoggerFactory.getLogger(QuarkusService.class);

    public boolean generateProjectService(Task task){
//        boolean result = true;
//        String targetPath = getPath("root",task);
//        logger.info("[generateProjectService] request: {}",task);
//        String extensions =Arrays.toString(task.getExtensions()).replaceAll("\\[|\\]|\\s+","");
//        String[] commands = new String[] {
//                "mvn",
//                "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
//                "-DprojectGroupId="+task.getGroupId(),
//                "-DprojectArtifactId="+task.getArtifactId(),
//                "-Dextensions=resteasy-reactive,smallrye-openapi,quarkus-resteasy-reactive-jackson,quarkus-hibernate-orm-rest-data-panache,"+extensions,
//        };
//
//        try {
//            Process process = Runtime.getRuntime().exec(commands, null, new File(targetPath));
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//                if (line.contains("BUILD FAILURE")){
//                    result = false;
//                }
//            }
            boolean result = createCommand(task);
            try{
            if (result){
                copyMvcTemplate(task);
                copyDockerTemplate(task);
                //TODO: replace package name function
                compressToZip(task);
            }
//            reader.close();
//
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean createCommand(Task task) {
        boolean result = true;
        String targetPath = getPath("root",task);
        logger.info("[generateProjectService] request: {}",task);
        String extensions =Arrays.toString(task.getExtensions()).replaceAll("\\[|\\]|\\s+","");
        String[] commands = new String[] {
                "mvn",
                "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
                "-DprojectGroupId="+task.getGroupId(),
                "-DprojectArtifactId="+task.getArtifactId(),
                "-Dextensions=resteasy-reactive,smallrye-openapi,quarkus-resteasy-reactive-jackson,quarkus-hibernate-orm-rest-data-panache,"+extensions,
        };

        try {
            Process process = Runtime.getRuntime().exec(commands, null, new File(targetPath));
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("BUILD FAILURE")) {
                    result = false;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public String getPath(String folderLayer, Task task) {
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            switch (folderLayer) {
                case "root":
                    return currentPath + "/temp";
                case "mvc":
                    return currentPath + "/temp/" + task.getArtifactId() + "/src/main/java/" + task.getGroupId().replace(".", "/");
                case "docker":
                    return currentPath + "/temp/" + task.getArtifactId() + "/src/main/docker";
                case "template":
                    return currentPath + "/template";
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void copyMvcTemplate(Task task) throws IOException {
        String source = getPath("template",task);
        String destination = getPath("mvc",task);
        logger.info("[copyMvcTemplate] copy path: {}{}", source, destination);
        String[] list = {"controller","model","service"};
        for (String subfolder:list) {
           File sourceDirectory = new File(source,subfolder);
           File destinationDirectory = new File(destination,subfolder);
           copyDirectory(sourceDirectory,destinationDirectory, task);
        }
    }

    public void copyDockerTemplate(Task task) throws IOException {
        String source = getPath("template",task);
        String destination = getPath("docker",task);
        File sourceDirectory = new File(source,"mysql");
        File destinationDirectory = new File(destination,"mysql");
        logger.info("[copyDockerTemplate] copy path: {}{}", sourceDirectory, destinationDirectory);
        copyDirectory(sourceDirectory,destinationDirectory, task);
//            FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
    }

    public void compressToZip(Task task ) throws IOException {
        String targetPath = getPath("root",task);
        FileOutputStream fos = new FileOutputStream(targetPath+"/"+task.getArtifactId()+".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(targetPath+"/"+task.getArtifactId());

        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }


    private static void copyDirectory(File sourceDir, File destDir, Task task) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        for (String f : sourceDir.list()) {
            File source = new File(sourceDir, f);
            File destination = new File(destDir, f);

            if (source.isDirectory()) {
                copyDirectory(source, destination, task);
            } else {
                copyFile(source, destination, task);
            }
        }
    }

    private static void copyFile(File sourceFile, File destinationFile, Task task) throws IOException {
        FileInputStream input = new FileInputStream(sourceFile);
        FileOutputStream output = new FileOutputStream(destinationFile);
            byte[] buf = new byte[1024];
            int bytesRead;

//        if(!task.getGroupId().equals("org.accolite")){
//            String text = new String(buf, StandardCharsets.UTF_8);
//            text = text.replace("org.accolite", task.getGroupId());
//            buf = text.getBytes();
//        }

//            if (text.contains("org.accolite")) {
//            text = text.replace("org.accolite", task.getGroupId());
//            buf=text.getBytes(StandardCharsets.UTF_8);
//            logger.info("input content {}",text);
//            }

            while ((bytesRead = input.read(buf)) != -1){
//                String text = new String(buf, StandardCharsets.UTF_8);
//                logger.info("input content before {} ", bytesRead);
//                if (text.contains("org.accolite")) {
//                    text = text.replace("org.accolite", task.getGroupId());
//                    byte[] newBuf = text.getBytes();
//                    int newBytesRead = input.read(text.getBytes());
//                     logger.info("input content{} {}",bytesRead, text);
//                    logger.info("input content after {} ", bytesRead);
//                    output.write(text.getBytes());
//
//                } else {
                    output.write(buf, 0, bytesRead);
//                output.write(buf);

//                }
            }
                input.close();
                output.close();
            }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
