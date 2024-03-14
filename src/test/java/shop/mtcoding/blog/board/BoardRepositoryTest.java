package shop.mtcoding.blog.board;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.List;

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
    public void findAll_test(){
        boardReposiroty.findAll();

    }

    @Test
    public void findAll_lazyLoading_test(){
       List<Board> boardList = boardReposiroty.findAll();
        boardList.forEach(board -> {
            System.out.println(board.getUser().getUsername());
        });

    }

    @Test
    public void findAllv2_lazyLoading_test(){
        List<Board> boardList = boardReposiroty.findAllV2();
        boardList.forEach(board -> {
            System.out.println(board.getUser());
        });



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

@Test
public void findAll_custom_inquery_test(){
    // given
    List<Board> boardList =boardReposiroty.findAll();
   int[] userIds = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();


    for(int i :userIds){
        System.out.println(i);
    }
//
//    boardList.stream().filter(board -> board.getUser().getId()==1);
 //select * from user_tb where id in (3,2,1) ;
    // then
}


@Test
public void randomquery_test(){
    // given

    int[] ids = {1,2}; // 이거를 포문돌면서

    //select q  = "select u from u where u.id in(:id1,:id2)"
    String q = "select u from User u where u.id id(";
    for(int i=0;i<ids.length;i++){
        if(i==ids.length-1){
            q=q+"?)";
        }else{
            q =q+"?,";
        }
    }
    System.out.println(q);
//    List<Integer> integers = Arrays.a


    // when

    // then

}

}
