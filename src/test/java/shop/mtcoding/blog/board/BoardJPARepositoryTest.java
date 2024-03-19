package shop.mtcoding.blog.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BoardJPARepositoryTest {

    @Autowired
    private BoardJPARepository boardJPARepository ;

    @Test
    public void findByIdJoinUserAndReplies_test(){
        // given
        int id = 4 ;
        // when
        Board board = boardJPARepository.findByIdJoinUserAndReplies(id).get();
        // then

    }
}
