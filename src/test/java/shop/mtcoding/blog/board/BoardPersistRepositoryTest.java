package shop.mtcoding.blog.board;


import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;


@Import(BoardPersistRepository.class)
@DataJpaTest
public class BoardPersistRepositoryTest {
    @Autowired
    private EntityManager em;

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

        //em.clear()는 pc 를 비움. 이거를 넣으면 캐싱하지 않고 다시 쿼리를 날림
//        em.clear();
        //같은 id 를 두 번 조회하면 두번쨰는 캐싱되기 때문에 쿼리가 실행되지 않음
        boardPersistRepository.findById(id);
        // 다른 id 를 조회하면 캐싱되지 않기 때문에 두번 실행됨
        boardPersistRepository.findById(2);

    }
    @Test
    public void deleteByIdV2_test(){
        int id =1 ;
        boardPersistRepository.deleteByIdV2(id);
        // 삭제가 안됨. 딜리트는 트랜잭션이 종료될 때 쿼리가 실행됨.
        // 근데 트랜잭션이 테스트 내부에 레파지토리 트랜잭션이 이중으로 되어있음.
        // 그래서 레파지토리의 트랜잭션이 완료가 되도 테스트의 트랜잭션이 종료되지 않음
        // 근데 테스트 코드가 완료되면 프로그램이 종료되기 때문에 쿼리가 날라가지 않음
        //근데 테스트에서는 안됨.

        //트랜잭션 종료 안되도 강제로 쿼리를 날림. 테스트에서만 사용할 수 있음.
        em.flush();

    }

    @Test
    public void deletById_test(){
        int id = 1 ;

        boardPersistRepository.deleteById(id);

    }


}
