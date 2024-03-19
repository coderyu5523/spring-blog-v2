package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.reply.Reply;
import shop.mtcoding.blog.user.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    //테이블은 생성되면 안됨. 조회된 것을 담는 용도로만 사용.
    //@ManyToOne 은 eager 가 기본, @OneToMany 는 lazy 가 기본
    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY, cascade = CascadeType.REMOVE) // Board 는 엔티티 객체의 필드명, reply 엔티티에 Board 객체를 넣는거임
    private List<Reply> replies = new ArrayList<>(); // 댓글이 없으면 null 일 때 오류남. 그래서 new 를 해서 크기를 0 으로 만들어놓는다.


    @Transient // 테이블 생성이 안됨. 임시로 사용함
    private boolean isBoardOwner ;

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



