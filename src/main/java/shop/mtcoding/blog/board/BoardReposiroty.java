package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class BoardReposiroty {

    private final EntityManager em;

    public Board findByIdJoinUser(int id){
        //join fetch 를 하면 b 를 조회했을 때 user의 객체도 같이 조회된다.
        Query query = em.createQuery("select b from Board b join fetch b.user u where b.id =:id",Board.class);
        query.setParameter("id",id);
        Board board = (Board) query.getSingleResult();
        return board;

    }

    public Board findById(int id){
        Board board = em.find(Board.class,id);
        return board;
    }

    public List<Board> findAll(){
        Query query = em.createQuery("select b from Board b order by b.id desc",Board.class);
        return query.getResultList();

    }

    public List<Board> findAllV2(){
        String q1 = "select b from Board b order by b.id desc";
        List<Board> boardList = em.createQuery(q1,Board.class).getResultList();

        //user_id 를 뽑아오기
        int[] userId = boardList.stream().mapToInt(board -> board.getUser().getId()).distinct().toArray();

        //인쿼리 select u from User u where u.id in(":id1",":id2",":id3")
        String q2 = "select u from User u where u.id in(";
        for(int i=0;i<userId.length;i++){
            if(i==userId.length-1){
                q2=q2+":id"+i+")";
            }else{
                q2=q2+":id"+i+",";
            }
        }
        Query query =em.createQuery(q2,User.class);

        for(int i =0;i<userId.length;i++){
            query.setParameter("id"+i,userId[i]);
        }
        List<User> userList = query.getResultList();

        boardList.stream().forEach(b -> {
            User user = userList.stream().filter(u -> u.getId() == b.getUser().getId()).findFirst().get();
            b.setUser(user);
        });

        //이렇게도 가능
//        for (Board board : boardList){
//            for (int i = 0; i < userList.size(); i++) {
//                User user = userList.get(i);
//                if (user.getId() == board.getUser().getId()){
//                    board.setUser(user);
//                }
//            }
//        }


        return boardList;  //user 가 채워져있어야 됨

    }

    @Transactional
    public void save(Board board) {
        em.persist(board);
        //persist 는 이미 있는 것이기 때문에 junit 테스트 필요없을 것 같다.

    }
    @Transactional
    public void deleteById(Integer id) {
        String q = """
                delete from Board b where b.id = :id 
                """;
        Query query = em.createQuery(q);
        query.setParameter("id",id);
        query.executeUpdate();

    }
    @Transactional
    public void updateById(Integer id, BoardRequest.UpdateDTO requestDTO) {
        Board board = findById(id);
        board.update(requestDTO);
    }
}
