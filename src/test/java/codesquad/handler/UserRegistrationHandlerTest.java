package codesquad.handler;

import codesquad.http.HttpHeaders;
import codesquad.http.HttpRequest;
import codesquad.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserRegistrationHandlerTest {

    UserRegistrationHandler userRegistrationHandler;

    @BeforeEach
    void setUp() {
        userRegistrationHandler = UserRegistrationHandler.getInstance();
    }

    @Nested
    class handle_메서드는 {

        @ParameterizedTest
        @ValueSource(strings = {"GET", "PUT", "DELETE"})
        void POST_메서드_요청이_아니면_예외가_발생한다(String httpMethod) {
            HttpRequest httpRequest = new HttpRequest(null, httpMethod, null, null, null, null);
            assertThatThrownBy(() -> userRegistrationHandler.handle(httpRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 요청은 POST 메서드여야 합니다.");
        }

        @Test
        void content_type이_x_www_form_urlencoded가_아니면_예외가_발생한다() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.getValue());
            HttpRequest httpRequest = new HttpRequest(null, "POST", null, null, httpHeaders, null);
            assertThatThrownBy(() -> userRegistrationHandler.handle(httpRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] request body 타입이 올바르지 않습니다.");
        }

        @Test
        void request_body가_없으면_예외가_발생한다() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.addValue(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.getValue());
            HttpRequest httpRequest = new HttpRequest(null, "POST", null, null, httpHeaders, "");
            assertThatThrownBy(() -> userRegistrationHandler.handle(httpRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] request body가 없습니다.");
        }
    }

}