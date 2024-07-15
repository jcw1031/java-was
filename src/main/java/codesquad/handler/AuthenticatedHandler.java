package codesquad.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;
import codesquad.http.session.SessionContextHolder;
import codesquad.http.session.SessionManager;
import codesquad.model.User;
import codesquad.model.UserDataBase;

import java.util.Optional;

public abstract class AuthenticatedHandler extends RequestHandler {

    private final SessionManager sessionManager = SessionManager.getInstance();
    private final UserDataBase userDataBase = UserDataBase.getInstance();

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String sessionId = SessionContextHolder.getSessionId();
        String foundUserId = sessionManager.findUserId(sessionId)
                .orElse("");
        Optional<User> foundUser = userDataBase.findUser(foundUserId);
        if (foundUser.isEmpty()) {
            return responseGenerator.sendRedirect(httpRequest, "/login");
        }

        return super.handle(httpRequest);
    }
}
