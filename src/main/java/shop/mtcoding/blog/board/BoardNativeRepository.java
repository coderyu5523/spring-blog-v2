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


}
