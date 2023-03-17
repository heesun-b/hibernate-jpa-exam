package shop.mtcoding.hiberpc.model.user;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    public User findById(int id) {
        return em.find(User.class, id);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }

    public User save(User user) {
        if (user.getId() == null) {
            em.persist(user);
        } else {
            // db에 해당 id를 가진 user가 있는지 확인하고 merge 해야 함
            // 그러나 dirty checking 할 것이라 아래 코드는 사용하지 않을 것임 - 개념만 이해
            User userPS = em.find(User.class, user.getId());
            if (userPS != null) {
                em.merge(user);
            } else {
                System.out.println("log : 잘못된 merge");
            }
        }
        return user;
    }

    public void delete(User user) {
        em.remove(user);
    }

}
