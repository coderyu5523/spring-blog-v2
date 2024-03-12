package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository
public class BoardNativeRepository {

    private final EntityManager em;


    @Transactional
    public void save(String title,String content, String username){
        Query query = em.createNativeQuery("insert into board_tb(title,content,username,created_at) values (?,?,?,now())");
        query.setParameter(1,title);
        query.setParameter(2,content);
        query.setParameter(3,username);
        query.executeUpdate();

    }

    public List<Board> findAll() {

        String q = """
                select * from board_tb order by id desc;
                """;
        Query query = em.createNativeQuery(q,Board.class);

        try {
            List<Board> boardList = query.getResultList();
            return boardList;
        } catch (Exception e) {
            return null;
        }

    }

    public Board findById(int id) {

        String q = """
                select * from board_tb where id =?;
                """;
        Query query = em.createNativeQuery(q,Board.class);
        query.setParameter(1,id);
        try {
           Board board = (Board) query.getSingleResult();
            return board;
        } catch (Exception e) {
            return null;
        }

    }

    @Transactional
    public void deleteById(Integer id) {
        String q = """
                delete from board_tb where id = ?
                """;


        Query query = em.createNativeQuery(q);
        query.setParameter(1,id);
        query.executeUpdate();
    }
    @Transactional
    public void updateById(String title,String content,String username,Integer id) {
        String q = """
                update board_tb set title = ? , content = ?, username =?  where id =?  
               
                """;
        Query query = em.createNativeQuery(q);
        query.setParameter(1,title);
        query.setParameter(2,content);
        query.setParameter(3,username);
        query.setParameter(4,id);
        query.executeUpdate();
    }
}
