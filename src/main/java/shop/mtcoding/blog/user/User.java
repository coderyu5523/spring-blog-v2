package shop.mtcoding.blog.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor  //  빈생성자가 필요
@Entity
@Data
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    @Column(unique = true)
    private String username ;
    private String password;
    private String email;
    @CreationTimestamp // persistance centext 에 전달될 때 자동으로 주입됨.
    private Timestamp createdAt;

    public User(String title, String content, String username) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


}
