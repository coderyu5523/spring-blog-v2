package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;
import shop.mtcoding.blog.user.UserRepository;

import java.util.List;


@RequiredArgsConstructor
@Controller
public class BoardController {
    private final BoardReposiroty boardReposiroty ;
    private final HttpSession session;

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO){
        //ORM 으로 INSERT 할 때, USER객체의 ID만 들어가있어도 된다.
        //즉 비영속 객체여도 된다. 하지만 없을 수도 있기 때문에 조회를 먼저 하는게 좋다.
        User sessionUser = (User) session.getAttribute("sessionUser");
        boardReposiroty.save(requestDTO.toEntity(sessionUser));
        return "redirect:/";
    }

    @GetMapping({ "/"})
    public String index(HttpServletRequest request) {
        List<Board> boardList = boardReposiroty.findAll();
        request.setAttribute("boardList",boardList);
        return "index"; // 리퀘스트디스패쳐 방식으로 가방을 내부적으로 전달.
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id,HttpServletRequest request) {  // int 를 쓰면 값이 없으면 0, Integer 를 넣으면 값이 없을 때 null 값이 들어옴.
//      Board board = boardReposiroty.findByIdJoinUser(id); 이건 조인해서 하는 것
       Board board = boardReposiroty.findById(id);

       // 수정삭제버튼 권한부여
        User sessionUser = (User) session.getAttribute("sessionUser");
        boolean owner = sessionUser != null && sessionUser.getId() == board.getUser().getId();
        request.setAttribute("owner", owner);
        request.setAttribute("board",board);

        return "board/detail";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){
        boardReposiroty.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/board/{id}/update-form")
     public String updateForm(@PathVariable Integer id,HttpServletRequest request){
        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id){

        return "redirect:/board/"+id ;
    }


}
