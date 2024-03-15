package shop.mtcoding.blog.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@Import(UserRepository.class) //ioc 등록코드
@DataJpaTest  //데이터 소스(conection pool), 엔티티 매니저,
public class UserRepository {

    @Autowired
    private UserRepository userRepository;




}
