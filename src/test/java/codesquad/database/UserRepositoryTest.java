package codesquad.database;

import codesquad.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest {

    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = UserRepository.getInstance();
    }

    @Nested
    class save_메서드는 {

        @AfterEach
        void tearDown() {
            userRepository.deleteAll();
        }

        @Test
        void User_인스턴스를_DB에_저장한다() throws InterruptedException {
            User user = new User("test", "테스트", "test");
            userRepository.save(user);

            Optional<User> foundUser = userRepository.findByUserId("test");
            assertThat(foundUser).isPresent();
            assertThat(foundUser.get().getUserId()).isEqualTo(user.getUserId());
        }
    }

    @Nested
    class findByUserId_메서드는 {

        @AfterEach
        void tearDown() {
            userRepository.deleteAll();
        }

        @Test
        void User_인스턴스를_찾아서_반환한다() {
            User user = new User("test", "테스트", "test");
            userRepository.save(user);

            Optional<User> foundUser = userRepository.findByUserId("test");
            assertThat(foundUser).isPresent();
            assertThat(foundUser.get().getUserId()).isEqualTo(user.getUserId());
        }

        @Test
        void User_인스턴스가_없으면_empty를_반환한다() {
            Optional<User> foundUser = userRepository.findByUserId("test");
            assertThat(foundUser).isEmpty();
        }
    }
}