package codesquad.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.MediaType;
import codesquad.http.session.SessionManager;
import codesquad.resource.DirectoryIndexResolver;
import codesquad.resource.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Optional;

public class DynamicResourceHandler extends RequestHandler {

    private static DynamicResourceHandler instance;

    private final DirectoryIndexResolver directoryIndexResolver = DirectoryIndexResolver.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();

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
        Optional<Resource> resolvedResource = directoryIndexResolver.resolve(uri);
        if (resolvedResource.isEmpty()) {
            return responseGenerator.sendNotFound(httpRequest);
        }

        Resource resource = resolvedResource.get();
        if (!resource.getExtension().equals("html")) {
            return responseGenerator.sendOK(resource.getContent(), MediaType.find(resource.getExtension()), httpRequest);
        }

        String sessionId = httpRequest.getCookie("sid");
        Optional<String> foundUserId = sessionManager.findUserId(sessionId);
        if (foundUserId.isEmpty()) {
            return responseGenerator.sendRedirect(httpRequest, "/login");
        }

        byte[] content = resource.getContent();
        String htmlText = new String(content);
        try (BufferedReader reader = new BufferedReader(new StringReader(htmlText))) {
            StringBuilder replacedHtml = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("id=\"headerButton\"")) {
                    if (httpRequest.hasSession()) {
                        line = line.replace("로그인", "로그아웃");
                    }
                }
                replacedHtml.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
