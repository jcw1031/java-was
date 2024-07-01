package codesquad.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;

public class ResourcesReader {

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final Logger log = LoggerFactory.getLogger(ResourcesReader.class);

    private ResourcesReader() {
    }

    public static String readResource(String path) {
        StringBuilder resource = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(RESOURCES_PATH + path)) {
            byte[] buffer = new byte[fileInputStream.available()];
            int data;
            while ((data = fileInputStream.read(buffer)) != -1) {
                String input = new String(buffer, 0, data);
                resource.append(input);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return resource.toString();
    }

}
