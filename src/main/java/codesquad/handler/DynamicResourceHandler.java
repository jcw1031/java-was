package codesquad.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.MediaType;
import codesquad.resource.Resource;
import codesquad.resource.ResourcesReader;

import java.util.Optional;

public class DynamicResourceHandler extends RequestHandler {

    private static DynamicResourceHandler instance;

    private DynamicResourceHandler() {
    }

    public static DynamicResourceHandler getInstance() {
        if (instance == null) {
            instance = new DynamicResourceHandler();
        }
        return instance;
    }

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        String uri = httpRequest.uri();

        Optional<Resource> readResource = ResourcesReader.readResource(uri);
        if (readResource.isPresent()) {
            Resource resource = readResource.get();
            if (resource.isFile()) {
                MediaType mediaType = MediaType.find(resource.getExtension());
                byte[] content = resource.getContent();
            }

            readResource = ResourcesReader.readResource(resource.getFileName() + "/index.html");
            if (readResource.isPresent()) {
                resource = readResource.get();
                MediaType mediaType = MediaType.find(resource.getExtension());
            }
        }
        return responseGenerator.sendNotFound(httpRequest);
    }
}
