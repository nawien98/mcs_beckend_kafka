package org.accolite.service;

import org.accolite.model.Task;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@ApplicationScoped
public class QuarkusService {
    private static final Logger logger = LoggerFactory.getLogger(QuarkusService.class);

    public boolean generateQuarkusService(Task task){
        boolean result = createCommand(task);

        try{
            if (result){
//                checkProjectExist(task);
                copyMvcTemplate(task);
                copyDockerTemplate(task);
//                writeSQLconfig(task);
                copyReadme(task);
                copyProperties(task);
                compressToZip(task);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean createCommand(Task task) {
        String os = System.getProperty("os.name");
        boolean result = true;
        String targetPath = getPath("root",task);
        logger.info("[generateProjectService] request: {}",task);
        String extensions =Arrays.toString(task.getExtensions()).replaceAll("\\[|\\]|\\s+","");

        String database = ConfigProvider.getConfig().getValue("quarkus."+task.getDatabase(), String.class);
        String[] commands = new String[] {
                "mvn",
                "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
                "-DprojectGroupId="+task.getGroupId(),
                "-DprojectArtifactId="+task.getArtifactId(),
                "-Dextensions=resteasy-reactive,smallrye-openapi,quarkus-resteasy-reactive-jackson,quarkus-hibernate-orm-rest-data-panache,"+database,
                "-DbuildTool="+task.getBuild(),
        };

        if (os.contains("Windows")){
            commands = new String[] {
                    "cmd",
                    "/c",
                    "mvn",
                    "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
                    "-DprojectGroupId="+task.getGroupId(),
                    "-DprojectArtifactId="+task.getArtifactId(),
                    "-Dextensions=resteasy-reactive,smallrye-openapi,quarkus-resteasy-reactive-jackson,quarkus-hibernate-orm-rest-data-panache,"+database,
                    "-DbuildTool="+task.getBuild(),
            };
        }

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

    public void updatePackageName(Task task){
        String str = "Append OutputStream Example \n";
        File textFile = new File("TextFile.txt");
        FileOutputStream fileOutputStream = null;
        try {
        //Create FileOutputStream with append flag as true
            fileOutputStream = new FileOutputStream(textFile, true);

        // Writes bytes to the stream
            fileOutputStream.write(str.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close the stream
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void checkProjectExist(Task task){
        String destination = getPath("root",task);
        File destinationDirectory = new File(destination,task.getArtifactId());
        if (destinationDirectory.exists()) {
            deleteDirectory(destinationDirectory);
        }

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
    }

    public void copyProperties(Task task) throws IOException {
        String source = getPath("template",task);
        String destination = getPath("root",task);
        File sourceDirectory = new File(source+"/"+"application.properties");
        File destinationDirectory = new File(destination+"/"+task.getArtifactId()+"/src/main/resources/application.properties");
        copyFile2(sourceDirectory,destinationDirectory,task);
    }

    public void copyReadme(Task task) throws IOException {
        String source = getPath("template",task);
        String destination = getPath("root",task);
        File sourceDirectory = new File(source+"/"+"README.md");
        File destinationDirectory = new File(destination+"/"+task.getArtifactId()+"/README.md");
        copyFile2(sourceDirectory,destinationDirectory,task);
    }

    public void compressToZip(Task task ) throws IOException {
        String source = getPath("root",task);
        FileOutputStream fos = new FileOutputStream(source+"/"+task.getArtifactId()+".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(source+"/"+task.getArtifactId());
        logger.info("[compressToZip] zip path: {} {}", source, fos);
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
                copyFile2(source, destination, task);
            }
        }
    }

    private void writeSQLconfig(Task task) throws IOException {
        String sourceFile = getPath("template/sqlConfig.yaml",task);
        String destFile = getPath("root",task);
        File destinationFile = new File(destFile+"/"+task.getArtifactId()+"/src/main/resources/application.properties");

        Scanner scan= new Scanner(sourceFile);
        String fileContent = "";
        while(scan.hasNext()){
            fileContent = fileContent.concat(scan.nextLine()+"\n");
        }
        FileWriter writer = new FileWriter(destinationFile);
        writer.write(fileContent);
        writer.close();
    }

    boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }


    private static void copyFile2(File sourceFile, File destinationFile, Task task) throws IOException {
        Scanner scan= new Scanner(sourceFile);
        String fileContent = "";
        while(scan.hasNext()){
            StringBuilder s = new StringBuilder(scan.nextLine());
            if (s.toString().contains("org.accolite")){
                s = new StringBuilder(s.toString().replace("org.accolite", task.getGroupId()));
            }
            if (s.toString().contains("@Column") && task.getEntities()!=null){
                String[][] entities = task.getEntities();
                for(int i = 0;i<entities.length;i++){
                    if (i != 0){
                        s.append("\n\t@Column\n").append("\tpublic ").append(entities[i][0]).append(" ").append(entities[i][1]).append(";\n");
                    }else{
                        s.append("\n" + "\tpublic ").append(entities[i][0]).append(" ").append(entities[i][1]).append(";\n");
                    }
                }
            }
            fileContent = fileContent.concat(s+"\n");
        }
//        fileContent = fileContent.replaceAll("org.accolite",task.getGroupId());
        FileWriter writer = new FileWriter(destinationFile);
        writer.write(fileContent);
        writer.close();
    }

    private static void copyFile(File sourceFile, File destinationFile, Task task) throws IOException {
        FileInputStream input = new FileInputStream(sourceFile);
        FileOutputStream output = new FileOutputStream(destinationFile);
            byte[] buf = new byte[1024];
            int bytesRead;

            while ((bytesRead = input.read(buf)) != -1){
                    output.write(buf, 0, bytesRead);
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
