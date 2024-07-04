package codesquad.handler;

import codesquad.http.HttpRequest;
import codesquad.http.HttpResponse;

public class UserRegistrationHandler extends RequestHandler {

    @Override
    public HttpResponse handle(HttpRequest httpRequest) {
        String userId = httpRequest.firstParameterValue("userId")
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 회원가입 아이디가 비어있습니다."));
        String nickname = httpRequest.firstParameterValue("nickname")
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 회원가입 닉네임이 비어있습니다."));
        String password = httpRequest.firstParameterValue("password")
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 회원가입 비밀번호가 비어있습니다."));

        return null;
    }

}
