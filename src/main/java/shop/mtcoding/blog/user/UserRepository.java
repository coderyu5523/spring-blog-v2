package shop.mtcoding.blog.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public User save(User user) {
        em.persist(user);
        return user;
    }
    @Transactional
    public User updateById(int id,UserRequest.UpdateDTO requestDTO){
        User user = findById(id);
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());

        return null;
    }

    public User findById(int id) {
       User user = em.find(User.class,id);
        return user;
    }
}
