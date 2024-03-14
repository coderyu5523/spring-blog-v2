package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
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

        Map<Integer,User> userMap =new HashMap<>();
        for(User user :userList){
            userMap.put(user.getId(),user);
        }
        for(Board board :boardList){
            userMap.get(board.getUser().getId());
        }

        return boardList;  //user 가 채워져있어야 됨

    }

}
