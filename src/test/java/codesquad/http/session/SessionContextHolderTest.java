package codesquad.http.session;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

class SessionContextHolderTest {

    @AfterEach
    void tearDown() {
        SessionContextHolder.clear();
    }

    @Nested
    class setSessionId_메서드는 {

        @Test
        void 문자열_값을_저장한다() {
            String sessionId = "test";
            SessionContextHolder.setContext(sessionId);
            assertThat(SessionContextHolder.getContext()).isEqualTo(sessionId);
        }
    }

    @Nested
    class getSessionId_메서드는 {

        @Test
        void 저장된_값을_반환한다() {
            String sessionId = "test";
            SessionContextHolder.setContext(sessionId);
            assertThat(SessionContextHolder.getContext()).isEqualTo(sessionId);
        }
    }

    @Nested
    class clear_메서드는 {

        @Test
        void 저장된_값을_제거한다() {
            String sessionId = "test";
            SessionContextHolder.setContext(sessionId);
            SessionContextHolder.clear();
            assertThat(SessionContextHolder.getContext()).isNull();
        }
    }

    @Nested
    class 멀티_스레드_환경에서 {

        @Test
        void 각_스레드별로_값을_저장한다() throws ExecutionException, InterruptedException {
            String sessionId = "thread1";
            String anotherThreadSessionId = "thread2";
            SessionContextHolder.setContext(sessionId);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            Future<?> future = executorService.submit(() -> {
                SessionContextHolder.setContext(anotherThreadSessionId);
                assertThat(SessionContextHolder.getContext()).isEqualTo(anotherThreadSessionId);
                return SessionContextHolder.getContext();
            });
            assertThat(SessionContextHolder.getContext()).isEqualTo(sessionId);
            assertThat(SessionContextHolder.getContext()).isNotEqualTo(future.get());
            assertThat(future.get()).isEqualTo(anotherThreadSessionId);
        }
    }
}