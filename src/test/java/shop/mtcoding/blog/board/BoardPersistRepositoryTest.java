package shop.mtcoding.blog.board;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {


    @Autowired //DI
    private BoardPersistRepository boardPersistRepository;

    @Test
    public void save_test() {
        //given
        Board board = new Board("제목5","내용5","ssar");

        //when
        boardPersistRepository.save(board);
        //then
        System.out.println(board);

    }
    @Test
    public void findAll_test(){
        //given

        //when
        List<Board> boardList = boardPersistRepository.findAll();
        //then
        System.out.println("findAll_test/size : "+boardList.size());
        System.out.println("findAll_test/username : "+boardList.get(2).getUsername());

        //org.assertj.core.apo
        Assertions.assertThat(boardList.size()).isEqualTo(4);
        Assertions.assertThat(boardList.get(2).getUsername()).isEqualTo("ssar");

    }

    @Test
    public void fintById_test(){
        int id = 1;
        boardPersistRepository.findById(id);
        //같은 id 를 두 번 조회하면 두번쨰는 캐싱되기 때문에 쿼리가 실행되지 않음
        boardPersistRepository.findById(id);
        // 다른 id 를 조회하면 캐싱되지 않기 때문에 두번 실행됨
        boardPersistRepository.findById(2);

    }



}
