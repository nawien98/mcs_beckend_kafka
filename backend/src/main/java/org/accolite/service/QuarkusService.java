package org.accolite.service;

import org.accolite.model.Task;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@ApplicationScoped
public class QuarkusService {
    private static final Logger logger = LoggerFactory.getLogger(QuarkusService.class);

    public boolean generateQuarkusService(Task task){
        logger.info("[generateQuarkusService] request: {}", task);
        checkProjectExist(task);

        boolean result = createCommand(task);
        try{
            if (result){
                copyControllerAndServiceTemplate(task);
                copyModelTemplate(task);
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


    /**
     * This function mainly check the project folder exist or not,if existed then delete it.
     * @param task
     */
    public void checkProjectExist(Task task){
        String destination = getPath("temp",task);
        File destinationDirectory = new File(destination,task.getArtifactId());
        boolean exist = destinationDirectory.exists();
        logger.info("[checkProjectExist] result: {}",exist);
        if (exist) {
            deleteDirectory(destinationDirectory);
        }
    }

    /**
     * This function mainly fetch the fields from request body, and make a list
     * @param task
     * @return arraylist
     */
    public static List<String> fetchExtensionList (Task task) {
        Set<String> excludeFields = Stream.of("language", "framework", "groupId","artifactId","build","deploy","entities")
                .collect(Collectors.toCollection(HashSet::new));
        List<String> extensionList = new ArrayList<>();
        try {
            for (Field field: task.getClass().getDeclaredFields()){
                field.setAccessible(true);
                Object value = field.get(task);
                if (value != null && !excludeFields.contains(field.getName())) {
                    if(field.getName().equals("monitoring")){ //TODO: Not sure grafana's dependency in quarkus
                        continue;
                    }
                    if(value.toString().equals("slf4j")){// slf4j is default logging in quarkus
                        continue;
                    }
                    String extension = ConfigProvider.getConfig().getValue("quarkus."+value, String.class);
                    extensionList.add(extension);
                }
            }
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
        logger.info("[fetchExtensionList] list: {}",extensionList);
        return extensionList;
    }


    /**
     * This function mainly execute "mvn" command to create a quarkus project.
     * @param task generate requirement
     * @return
     */
    public boolean createCommand(Task task) {
        String os = System.getProperty("os.name");
        boolean result = true;
        String targetPath = getPath("temp",task);
        logger.info("[createCommand] request: {}",task);
//        String extensions =Arrays.toString(task.getExtensions()).replaceAll("\\[|\\]|\\s+","");
        String extensions = String.join(",", fetchExtensionList(task));
        String[] commands = new String[] {
                "mvn",
                "io.quarkus.platform:quarkus-maven-plugin:2.8.3.Final:create",
                "-DprojectGroupId="+task.getGroupId(),
                "-DprojectArtifactId="+task.getArtifactId(),
                "-Dextensions=resteasy-reactive,smallrye-openapi,quarkus-resteasy-reactive-jackson,quarkus-hibernate-orm-rest-data-panache,"+extensions,
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
                    "-Dextensions=resteasy-reactive,smallrye-openapi,quarkus-resteasy-reactive-jackson,quarkus-hibernate-orm-rest-data-panache,"+extensions,
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

    /**
     * This function mainly get the folder path
     * @param folderLayer
     * @param task
     * @return
     */

    public String getPath(String folderLayer, Task task) {
        try {
            String currentPath = new java.io.File(".").getCanonicalPath();
            switch (folderLayer) {
                case "temp":
                    return currentPath + "/temp";
                case "project":
                    return currentPath + "/temp/" + task.getArtifactId();
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

    /**
     * This function mainly copy the controller and service directory to generated project
     * @param task
     * @throws IOException
     */
    public void copyControllerAndServiceTemplate(Task task) throws IOException {
        String source = getPath("template",task);
        String destination = getPath("mvc",task);
        logger.info("[copyControllerAndServiceTemplate] copy path: {}, {}", source, destination);
        String[] list = {"controller","service"};
        for (String subfolder:list) {
           File sourceDirectory = new File(source,subfolder);
           File destinationDirectory = new File(destination,subfolder);
           copyDirectory(sourceDirectory,destinationDirectory, task);
        }
    }

    /**
     * This function mainly copy the model directory to generated project
     * @param task
     * @throws IOException
     */
    private void copyModelTemplate(Task task) throws IOException {
        String source = getPath("template", task);
        String destination = getPath("mvc", task);
        File sourceDirectory = new File(source + "/model/Default.java");
        File destinationDirectory = new File(destination ,"model");
        destinationDirectory.mkdir();
        logger.info("[copyModelTemplate] copy path: {}{}", source, destination);
        for (int i = 0; i <task.getEntities().size(); i++){
            Scanner scan = new Scanner(sourceDirectory);
            File destinationFile = new File(destination + "/model/" + task.getEntities().get(i).getEntity_name() + ".java");
            destinationFile.createNewFile();
            String fileContent = "";
            while (scan.hasNext()) {
                StringBuilder s = new StringBuilder(scan.nextLine());
                if (s.toString().contains("org.accolite")){// change "org.accolite" to input's group id
                    s = new StringBuilder(s.toString().replace("org.accolite", task.getGroupId()));
                }
                if (s.toString().contains("Default")){// change class name "Default" to input's entity name
                    s = new StringBuilder(s.toString().replace("Default", task.getEntities().get(i).getEntity_name()));
                }
                if (s.toString().contains("default")){// change table name "default" to input's entity name
                    s = new StringBuilder(s.toString().replace("default", task.getEntities().get(i).getEntity_name().toLowerCase()+"s"));
                }
                if (s.toString().contains("@Column") && task.getEntities()!=null){
                    String[][] fields = task.getEntities().get(i).getFields();
                    s.append(addColumns(fields));
                }
                fileContent = fileContent.concat(s + "\n");
            }
            FileWriter writer = new FileWriter(destinationFile);
            writer.write(fileContent);
            writer.close();
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
        String destination = getPath("temp",task);
        File sourceDirectory = new File(source+"/"+"application.properties");
        File destinationDirectory = new File(destination+"/"+task.getArtifactId()+"/src/main/resources/application.properties");
        copyFile(sourceDirectory,destinationDirectory,task);
    }

    public void copyReadme(Task task) throws IOException {
        String source = getPath("template",task);
        String destination = getPath("temp",task);
        File sourceDirectory = new File(source+"/"+"README.md");
        File destinationDirectory = new File(destination+"/"+task.getArtifactId()+"/README.md");
        copyFile(sourceDirectory,destinationDirectory,task);
    }

    public void compressToZip(Task task ) throws IOException {
        String source = getPath("temp",task);
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
                copyFile(source, destination, task);
            }
        }
    }

    private void writeSQLconfig(Task task) throws IOException {
        String sourceFile = getPath("template/sqlConfig.yaml",task);
        String destFile = getPath("temp",task);
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

    private void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    private static StringBuilder addColumns(String[][] fields){
        StringBuilder s = new StringBuilder();
        for(int i = 0;i<fields.length;i++){
            if (i != 0){
                s.append("\n\t@Column\n").append("\tpublic ").append(fields[i][0]).append(" ").append(fields[i][1]).append(";\n");
            }else{
                s.append("\n" + "\tpublic ").append(fields[i][0]).append(" ").append(fields[i][1]).append(";\n");
            }
        }
        return s;
    }

    private static void copyFile(File sourceFile, File destinationFile, Task task) throws IOException {
        Scanner scan= new Scanner(sourceFile);
        String fileContent = "";
        while(scan.hasNext()){
            StringBuilder s = new StringBuilder(scan.nextLine());
            if (s.toString().contains("org.accolite")){
                s = new StringBuilder(s.toString().replace("org.accolite", task.getGroupId()));
            }
            fileContent = fileContent.concat(s+"\n");
        }
        FileWriter writer = new FileWriter(destinationFile);
        writer.write(fileContent);
        writer.close();
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
