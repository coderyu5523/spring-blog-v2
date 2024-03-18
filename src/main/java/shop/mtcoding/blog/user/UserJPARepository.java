package shop.mtcoding.blog.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

//인터페이스는 인터페이스를 상속해야 한다. 문법
//인터페이스는 new 못함  implements 해서 그 클래스를 new 해야됨
//자동 컴퍼넌트 스캔됨. 인터페이스는 new 안되니까 자동으로 임의의 클래스를 만들어서 내부 구현체를 만들어서 new 해줌.
public interface UserJPARepository extends JpaRepository<User,Integer> { // <오브젝트 타입, pk의 타입>

    //메서드 쿼리. 쿼리 이름을 자동으로 쿼리로 만들어줌, 간단한건 괜찮지만 복잡하면 그냥 쿼리 적자.
    //@Query("select u from User u where u.username = :username and u.password=:password")
    Optional<User> findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
    Optional<User> findByUsername(@Param("username")String username);
    //단건을 조회했을 떄 중복을 막을 수 있음.

}


