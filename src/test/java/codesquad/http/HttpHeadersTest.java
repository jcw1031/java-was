package codesquad.http;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class HttpHeadersTest {

    @Nested
    class fromText_메서드는 {

        @Nested
        class 비어있는_값이_들어오면 {

            String emptyHeaders = "";

            @Test
            void 예외가_발생한다() {
                assertThatThrownBy(() -> HttpHeaders.fromText(emptyHeaders))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessageContaining("emptyHeaders");
            }
        }

    }

}