package shop.mtcoding.blog.board;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(BoardReposiroty.class)
@DataJpaTest
public class BoardRepositoryTest {
    @Autowired
    private BoardReposiroty boardReposiroty;

    @Test
    public void findByIdJoinUser_test(){
        int id = 1;
        boardReposiroty.findByIdJoinUser(id);

    }


    @Test
    public void findById_test(){
        int id = 1 ;

        Board board = boardReposiroty.findById(id);
        System.out.println("start-2");
        System.out.println(board.getUser().getId());
        System.out.println("start-3");
        System.out.println(board.getUser().getUsername());
    }

}
