package codesquad.handler;

import codesquad.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HandlersMapper {

    private final Logger log = LoggerFactory.getLogger(HandlersMapper.class);

    private final Map<String, RequestHandler> requestHandlers = Map.of(
            "/user/login", UserLoginHandler.getInstance(),
            "/user/logout", UserLogoutHandler.getInstance(),
            "/user/create", UserRegistrationHandler.getInstance(),
            "/user/list", UserListHandler.getInstance(),
            "/", DynamicResourceHandler.getInstance()
    );

    public RequestHandler getRequestHandler(HttpRequest httpRequest) {
        String uri = httpRequest.uri();
        log.info("uri 먼가요 = {}", uri);
        return requestHandlers.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equals(uri))
                .findAny()
                .map(Map.Entry::getValue)
                .orElse(DynamicResourceHandler.getInstance());
    }

}
