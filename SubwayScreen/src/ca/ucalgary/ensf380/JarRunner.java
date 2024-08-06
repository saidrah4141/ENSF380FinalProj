package ca.ucalgary.ensf380;

import java.io.File;
import java.io.IOException;

/**
 * A utility class for running a JAR file with specified input and output paths.
 * It sets up and executes the JAR file as an external process using the Java ProcessBuilder.
 */
public class JarRunner {

    private final String jarFilePath;
    private final String inputPath;
    private final String outputPath;

    /**
     * Constructs a JarRunner with the specified JAR file path, input path, and output path.
     * 
     * @param jarFilePath the path to the JAR file to be executed
     * @param inputPath the path to the input file or directory for the JAR
     * @param outputPath the path to the output file or directory for the JAR
     */
    public JarRunner(String jarFilePath, String inputPath, String outputPath) {
        this.jarFilePath = jarFilePath;
        this.inputPath = inputPath;
        this.outputPath = outputPath;
    }

    /**
     * Runs the JAR file with the specified input and output paths.
     * The method uses Java's ProcessBuilder to set up and execute the JAR file as an external process.
     * It redirects error and output streams to the console and waits for the process to complete.
     * The exit code of the process is printed to the console.
     */
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
