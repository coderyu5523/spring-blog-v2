package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog.board.Board;

@RequiredArgsConstructor
@Repository
public class UserRepository {

    private final EntityManager em;

    public User findByUsernameAndPassword(UserRequest.LoginDTO requestDTO) {
        String q = """
                select u from User u where u.username = :username and u.password =:password
                """;
        Query query = em.createQuery(q, User.class);
        query.setParameter("username",requestDTO.getUsername());
        query.setParameter("password",requestDTO.getPassword());

        return (User) query.getSingleResult();

    }
    @Transactional
    public void save(UserRequest.JoinDTO requestDTO) {
        User user = new User();
        user.setUsername(requestDTO.getUsername());
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());

        em.persist(user);

    }
}
