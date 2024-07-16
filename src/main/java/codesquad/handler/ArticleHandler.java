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

public class ArticleHandler extends AuthenticatedHandler {

    private static ArticleHandler instance;

    private final DirectoryIndexResolver directoryIndexResolver = DirectoryIndexResolver.getInstance();

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
        Resource resource = directoryIndexResolver.resolve(uri)
                .orElseThrow(() -> new HttpStatusException(StatusCode.NOT_FOUND, "[ERROR] 파일을 찾을 수 없습니다."));
        String content = new String(resource.getContent());
        SessionContext context = SessionContextHolder.getContext();
        String replacedHtml = HtmlTransformer.replaceUserHeader(content, context.user());
        return responseGenerator.sendOK(replacedHtml.getBytes(), MediaType.find(resource.getExtension()), httpRequest);
    }
}
