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

    //1건 찾 을 떄는 jpql 필요없음
    public Board findById(int id){
        Board board = em.find(Board.class,id);

        return board;
    }


    @Transactional
    public void deleteById(int id){
        Query query = em.createQuery("delete from Board b where b.id =:id");
        query.setParameter("id",id);
        query.executeUpdate();
        //버전2보다 버전1 추천. 여기는 삭제 기능만 처리되고, 조회는 서비스 레이어에서 하는게 맞음
    }
    @Transactional
    public void deleteByIdV2(int id){
        //우선 조회를 하면 pc에 데이터가 남아있기 때문에삭제할 수 있음
        Board board =findById(id);
        em.remove(board); //pc에 객체를 지우고, 트랜잭션 종료시 쿼리가 전송됨.

    }

    @Transactional
    public void updateById(Integer id,BoardRequest.UpdateDTO requestDTO) {
        Board board = findById(id);
        board.update(requestDTO);
        // 업데이트는 조회된 영속 객체를 수정해서 db에 업데이트.
        // 더티체킹이 핵심. 영속 객체를 조회된 객체만 수정됨.
        //영속화된 객체의 상태를 변경하고, 트랜잭션이 종료되면 더티체킹이 완료된다.

    }
}
