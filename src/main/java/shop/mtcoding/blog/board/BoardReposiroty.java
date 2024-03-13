package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class BoardReposiroty {

    private final EntityManager em;

    public Board findByIdJoinUser(int id){
        //join fetch 를 하면 b 를 조회했을 때 user의 객체도 같이 조회된다.
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id =:id",Board.class);
        query.setParameter("id",id);
        Board board = (Board) query.getSingleResult();
        return board;

    }

    public Board findById(int id){
        Board board = em.find(Board.class,id);
        return board;
    }

}
