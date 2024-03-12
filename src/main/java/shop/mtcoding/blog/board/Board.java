package shop.mtcoding.blog.board;


import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
//@NoArgsConstructor // 파싱할 때 빈생성자가 넣어줌. 근데 세터가 없는데 어떻게 데이터를 넣지? 리플렉션을 활용
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
    private Timestamp createdAt;

//    public void update(String title,String content){  //필요한 데이터만 변경할 수 있는 메서드를 만듬
//        this.title = title;
//        this.content = content ;
}



