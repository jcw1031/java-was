package codesquad.http.session;

import codesquad.http.HttpRequest;
import codesquad.model.UserDataBase;

public class SessionContextFactory {

    private static SessionContextFactory instance;

    private final SessionManager sessionManager = SessionManager.getInstance();
    private final UserDataBase userDataBase = UserDataBase.getInstance();

    private SessionContextFactory() {
    }

    public static SessionContextFactory getInstance() {
        if (instance == null) {
            instance = new SessionContextFactory();
        }
        return instance;
    }

    public void createSessionContext(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookie("sid");
        String foundUserId = sessionManager.findUserId(sessionId)
                .orElse("");
        userDataBase.findUser(foundUserId)
                .ifPresentOrElse(user -> SessionContextHolder.setSessionId(sessionId, user),
                        () -> SessionContextHolder.setSessionId(sessionId, null));
    }
}
