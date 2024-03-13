package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class BoardPersistRepository {
    private final EntityManager em ;

    @Transactional
    public Board save(Board board){
        // 얘를 persistance context에 던짐. entity 만 들어갈 수 있음. DTO 는 못 들어감.
        //비영속 객체
//        Board board =new Board(title,content,username) ;

        em.persist(board); // insert. pc에 전달
        //board - > pc에 전달된 후에는 영속 객체가 됨.
        return board ; // id를 찾을 때 max 값을 찾지 않아도 됨.
        //return 도 안해도 됨

    }

    public List<Board> findAll(){
        Query query = em.createQuery("select b from Board b order by b.id desc",Board.class);
        return query.getResultList() ;
    }


}
