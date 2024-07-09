package codesquad.handler;

import codesquad.handler.dto.LoginRequest;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.session.SessionManager;
import codesquad.model.User;
import codesquad.model.UserDataBase;

import java.util.Optional;

public final class UserLoginHandler extends RequestHandler {

    private static UserLoginHandler instance = new UserLoginHandler();

    private final ObjectMapper objectMapper = ObjectMapper.getInstance();
    private final UserDataBase userDataBase = UserDataBase.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();

    private UserLoginHandler() {
    }

    public static UserLoginHandler getInstance() {
        if (instance == null) {
            instance = new UserLoginHandler();
        }
        return instance;
    }

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        RequestValidator.validatePostRequest(httpRequest);

        String body = httpRequest.body()
                .orElseThrow(() -> new IllegalStateException("[ERROR] request body가 없습니다."));
        LoginRequest loginRequest = objectMapper.readQueryString(body, LoginRequest.class);

        Optional<User> user = userDataBase.findUser(loginRequest.userId());
        if (user.isEmpty() || !user.get().matchPassword(loginRequest.password())) {
            return responseGenerator.sendRedirect(httpRequest, "/login/failed.html");
        }

        String sessionId = sessionManager.createSession(user.get().getUserId());
        HttpResponse httpResponse = responseGenerator.sendRedirect(httpRequest, "/main");
        httpResponse.addCookie("sid", sessionId, "/");
        return httpResponse;
    }

}
