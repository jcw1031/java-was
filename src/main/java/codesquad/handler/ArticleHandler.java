package codesquad.handler;

import codesquad.error.HttpStatusException;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.MediaType;
import codesquad.http.StatusCode;
import codesquad.resource.DirectoryIndexResolver;
import codesquad.resource.Resource;

public class ArticleHandler extends AuthenticatedHandler {

    private static ArticleHandler instance;

    private ArticleHandler() {
    }

    public static ArticleHandler getInstance() {
        if (instance == null) {
            instance = new ArticleHandler();
        }
        return instance;
    }

    @Override
    protected HttpResponse handleGet(HttpRequest httpRequest) {
        String uri = httpRequest.uri();
        Resource resource = DirectoryIndexResolver.getInstance()
                .resolve(uri)
                .orElseThrow(() -> new HttpStatusException(StatusCode.NOT_FOUND, "[ERROR] 파일을 찾을 수 없습니다."));
        return responseGenerator.sendOK(resource.getContent(), MediaType.find(resource.getExtension()), httpRequest);
    }
}
