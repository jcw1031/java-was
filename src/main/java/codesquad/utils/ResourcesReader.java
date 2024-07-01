package codesquad.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class ResourcesReader {

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final Logger log = LoggerFactory.getLogger(ResourcesReader.class);

    private ResourcesReader() {
    }

    public static String readResource(String path) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(RESOURCES_PATH + path));
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            log.error(e.getMessage());
            return "";
        }
    }

}
