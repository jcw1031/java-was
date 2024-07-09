package codesquad.handler;

import codesquad.handler.dto.LoginRequest;
import codesquad.http.HttpHeaders;
import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.MediaType;
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
        if (!httpRequest.method().equals("POST")) {
            // TODO 405 Method Not Allowed 적용
            throw new IllegalArgumentException("[ERROR] 회원가입 요청은 POST 메서드여야 합니다.");
        }

        String contentType = httpRequest.firstHeaderValue(HttpHeaders.CONTENT_TYPE)
                .orElse("");
        if (!contentType.equals(MediaType.APPLICATION_FORM_URLENCODED.getValue())) {
            // TODO 400 Bad Request 적용
            throw new IllegalArgumentException("[ERROR] request body 타입이 올바르지 않습니다.");
        }

        String body = httpRequest.body()
                .orElseThrow(() -> new IllegalStateException("[ERROR] request body가 없습니다."));
        LoginRequest loginRequest = objectMapper.readQueryString(body, LoginRequest.class);

        Optional<User> user = userDataBase.findUser(loginRequest.userId());
        if (user.isEmpty() || !user.get().matchPassword(loginRequest.password())) {
            return responseGenerator.sendRedirect(httpRequest, "/login/failed.html");
        }

        // TODO 세션 생성 및 쿠키 설정
        return responseGenerator.sendRedirect(httpRequest, "/");
    }

}
