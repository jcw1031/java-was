package codesquad.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

public final class ResourcesReader {

    private static final String RESOURCES_PATH = "src/main/resources/";
    private static final Logger log = LoggerFactory.getLogger(ResourcesReader.class);

    private ResourcesReader() {
    }

    public static Optional<String> readResource(String path) {
        File file = new File(RESOURCES_PATH + path);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String content = reader.lines()
                    .collect(Collectors.joining(System.lineSeparator()));
            return Optional.of(content);
        } catch (IOException e) {
            log.error("[ERROR] 파일을 읽을 수 없습니다.", e);
            return Optional.empty();
        }
    }

}
