package codesquad.resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class ImageWriter {

    public static String write(Resource resource) {
        String directory = System.getProperty("user.home") + "/images";
        String uniqueFileName = UUID.randomUUID() + "." + resource.getExtension();
        File file = new File(directory, uniqueFileName);

        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(resource.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uniqueFileName;
    }
}
