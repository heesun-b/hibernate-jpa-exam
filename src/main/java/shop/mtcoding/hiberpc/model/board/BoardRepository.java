package shop.mtcoding.hiberpc.model.board;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {
    private final EntityManager em;

    public Board findById(int id) {
        return em.find(Board.class, id);
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    public Board save(Board entity) {
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            // db에 해당 id를 가진 user가 있는지 확인하고 merge 해야 함
            // 그러나 dirty checking 할 것이라 아래 코드는 사용하지 않을 것임 - 개념만 이해
            Board entityPS = em.find(Board.class, entity.getId());
            if (entityPS != null) {
                em.merge(entity);
            } else {
                System.out.println("log : 잘못된 merge");
            }
        }
        return entity;
    }

    public void delete(Board entity) {
        em.remove(entity);
    }

}
