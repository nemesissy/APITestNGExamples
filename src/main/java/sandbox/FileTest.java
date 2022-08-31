package sandbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileTest {

    private static final Logger logger = LogManager.getLogger(FileTest.class);

    public static void main(final String... args) {

        logger.debug("Entering application.");

        String fileName = "states.json";

        FileTest ft = new FileTest();
        ft.getFile(fileName);

        logger.debug("Exiting application.");
    }

    public void getFile(String filename) {

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(filename);
        if (resource == null) {
            logger.error("file not found! " + filename);
            throw new IllegalArgumentException("file not found! " + filename);
        }
        File file = null;
        try {
            file = new File(resource.toURI());
        } catch (Exception c) {
            logger.error("Unhandled exception on file " + filename, c);
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (Exception c) {
            logger.error("Unhandled exception on file " + filename, c);
        }
        lines.forEach(System.out::println);
    }
}