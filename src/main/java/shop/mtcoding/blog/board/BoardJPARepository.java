package shop.mtcoding.blog.board;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.mtcoding.blog.user.User;

import java.util.Optional;

public interface BoardJPARepository extends JpaRepository<Board,Integer> {

    @Query("select b from Board b join fetch b.user where b.id = :id")
    Optional<Board> findByIdJoinUser(@Param("id") int id);
}
