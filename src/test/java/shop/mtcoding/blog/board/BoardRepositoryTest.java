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

    public void findByIdJoinUser_test(){

    }


    @Test
    public void findById_test(){
        int id = 1 ;

        System.out.println("start-1");
        Board board = boardReposiroty.findById(1);
        System.out.println("start-2");
        System.out.println(board.getUser().getId());
        System.out.println("start-3");
        System.out.println(board.getUser().getUsername());
    }

}
