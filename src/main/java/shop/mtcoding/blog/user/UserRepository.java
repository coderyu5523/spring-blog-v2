package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public void findByUsername(UserRequest.LoginDTO requestDTO) {
        String q = """
                select u from User u where u.username = :username and u.password =:password
                """;
        Query query = em.createQuery(q, User.class);
        query.setParameter("username",requestDTO.getUsername());
        query.setParameter("password",requestDTO.getPassword());


    }
}
