package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
@NoArgsConstructor  // 빈생성자가 필요
@Entity
@Data
@Table(name = "board_tb")
public class Board {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    private  String title;
    private  String content;

    //@JoinColumn(name="user_id") 변수명을 직접 지정 가능


    @ManyToOne(fetch = FetchType.LAZY)
    private User user ;  // 변수명이 user. user_id 를 만들어줌


    @CreationTimestamp // persistance centext 에 전달될 때 자동으로 주입됨.
    private Timestamp createdAt;

    @Builder  //엔티티에는 다 걸기
    public Board(Integer id, String title, String content, User user, Timestamp createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
    }

    public void update(BoardRequest.UpdateDTO requestDTO){
        this.title =requestDTO.getTitle();
        this.content = requestDTO.getContent() ;
    }

}



