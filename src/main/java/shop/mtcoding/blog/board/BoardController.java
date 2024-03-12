package shop.mtcoding.blog.board;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardNativeRepository boardNativeRepository ;

    @PostMapping("/board/save")
    public String save(String title, String content,String username){ //DTO 없이 데이터 받음

        boardNativeRepository.save(title,content,username);


        return "redirect:/";
    }

    @GetMapping({ "/"})
    public String index(HttpServletRequest request) {
        List<Board> boardList =  boardNativeRepository.findAll();
        request.setAttribute("boardList",boardList);
        return "index"; // 리퀘스트디스패쳐 방식으로 가방을 내부적으로 전달.
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id,HttpServletRequest request) {  // int 를 쓰면 값이 없으면 0, Integer 를 넣으면 값이 없을 때 null 값이 들어옴.

       Board board = boardNativeRepository.findById(id);
        request.setAttribute("board",board);

        return "board/detail";
    }
}