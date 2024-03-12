package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import shop.mtcoding.blog.util.MyDateUtil;

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
    private  String username;

    @CreationTimestamp // persistance centext 에 전달될 때 자동으로 주입됨.
    private Timestamp createdAt;

    public Board(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }

    public String getTime(){
        return MyDateUtil.timestampFormat(createdAt);
    }

//    public void update(String title,String content){  //필요한 데이터만 변경할 수 있는 메서드를 만듬
//        this.title = title;
//        this.content = content ;
}



