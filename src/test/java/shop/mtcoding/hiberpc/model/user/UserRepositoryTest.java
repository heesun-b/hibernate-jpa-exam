package shop.mtcoding.hiberpc.model.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import shop.mtcoding.hiberpc.config.dummy.MyDummyEntity;

@Import(UserRepository.class)
@DataJpaTest
public class UserRepositoryTest extends MyDummyEntity {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    public void setUp() {
        em.createNativeQuery("ALTER TABLE user_tb ALTER COLUMN id RESTART WITH 1").executeUpdate();
    }

    @Test
    public void save_test() {
        // given
        User user = newUser("ssar");

        // when
        User userPS = userRepository.save(user);

        // then
        assertThat(userPS.getId()).isEqualTo(1);
    }

    @Test
    public void update_test() {
        // given 1 - 영속화 : db에 더미 넣기

        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2 - request로 받을 데이터
        String pw = "5678";
        String email = "ssar@gmail.com";

        // when
        userPS.update(pw, email);
        User updateUserPS = userRepository.save(userPS);

        // then
        assertThat(updateUserPS.getPassword()).isEqualTo("5678");
        // 전체 test 시 여기서 오류 터지는 이유 : auto-increment는 초기화 안 돼서
    }

    @Test
    public void update_dutty_checking_test() {
        // given 1 - 영속화 : db에 더미 넣기

        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2 - request로 받을 데이터
        String pw = "5678";
        String email = "ssar@gmail.com";

        // when
        userPS.update(pw, email);
        // 해당 유저가 있는지 없는지 확인해야 해서 EntityManager 필요
        em.flush();

        // then
        User updateUserPS = userRepository.findById(1);
        assertThat(updateUserPS.getPassword()).isEqualTo("5678");
    }

    @Test
    public void delete_test() {
        // given 1 - 영속화 : db에 더미 넣기
        User user = newUser("ssar");
        User userPS = userRepository.save(user);

        // given 2 - 삭제할 user id
        int id = 1;
        User findUserPS = userRepository.findById(id);

        // when
        userRepository.delete(findUserPS);

        // then
        User deleteUserPS = userRepository.findById(id);
        assertThat(deleteUserPS).isNull();
    }

    @Test
    public void findById_test() {
        // given
        User user = newUser("ssar");
        User userPS = userRepository.save(user);
        // given 2
        int id = 1;

        // when
        User fidnUserPS = userRepository.findById(id);

        // then
        assertThat(fidnUserPS.getUsername()).isEqualTo("ssar");
    }

    @Test
    public void findAll_test() {

        List<User> userList = Arrays.asList(newUser("ssar"), newUser("cos"));

        userList.stream().forEach((user) -> {
            userRepository.save(user);
        });

        // when
        List<User> userListPS = userRepository.findAll();

        // then
        assertThat(userListPS.size()).isEqualTo(2);
    }
}
