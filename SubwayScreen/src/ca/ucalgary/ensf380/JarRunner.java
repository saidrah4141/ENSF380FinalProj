package ca.ucalgary.ensf380;

import java.io.File;
import java.io.IOException;

public class JarRunner {

    private final String jarFilePath;
    private final String inputPath;
    private final String outputPath;

    public JarRunner(String jarFilePath, String inputPath, String outputPath) {
        this.jarFilePath = jarFilePath;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }
    public void runJar() {
        ProcessBuilder processBuilder = new ProcessBuilder(
            "java", "-jar", jarFilePath, "--in", inputPath, "--out", outputPath
        );
        
        
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        try {
            
            Process process = processBuilder.start();

           
            int exitCode = process.waitFor();
            System.out.println("Process exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

   
}
