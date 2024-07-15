package codesquad.handler;

import codesquad.error.HttpStatusException;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.MediaType;
import codesquad.http.StatusCode;
import codesquad.http.session.SessionContext;
import codesquad.http.session.SessionContextHolder;
import codesquad.resource.DirectoryIndexResolver;
import codesquad.resource.Resource;
import codesquad.resource.transform.HtmlTransformer;

public class DynamicResourceHandler extends RequestHandler {

    private static DynamicResourceHandler instance;

    private final DirectoryIndexResolver directoryIndexResolver = DirectoryIndexResolver.getInstance();

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
        Resource resource = directoryIndexResolver.resolve(uri)
                .orElseThrow(() -> new HttpStatusException(StatusCode.NOT_FOUND, "[ERROR] 파일을 찾을 수 없습니다."));
        if (!resource.getExtension().equals("html")) {
            return responseGenerator.sendOK(resource.getContent(), MediaType.find(resource.getExtension()), httpRequest);
        }

        String content = new String(resource.getContent());
        SessionContext sessionContext = SessionContextHolder.getContext();
        String replacedHtml = HtmlTransformer.replaceUserHeader(content, sessionContext.user());
        return responseGenerator.sendOK(replacedHtml.getBytes(), MediaType.TEXT_HTML, httpRequest);
    }
}
