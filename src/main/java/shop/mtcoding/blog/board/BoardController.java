package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.mtcoding.blog._core.err.exception.Exception403;
import shop.mtcoding.blog._core.err.exception.Exception404;
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

//    @PostMapping("/board/{id}/delete")
    @RequestMapping(value = "/board/{id}/delete", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(@PathVariable Integer id){
        Board board = boardReposiroty.findById(id);
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser.getId()!=board.getUser().getId()){
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }
        boardReposiroty.deleteById(id);

        return "redirect:/";
    }

    @GetMapping("/board/{id}/update-form")
     public String updateForm(@PathVariable Integer id,HttpServletRequest request){
        Board board = boardReposiroty.findById(id);
        if(board==null){
            throw new Exception404("해당 게시글을 찾을 수 없습니다.");
        }
        request.setAttribute("board",board);

        return "board/update-form";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable Integer id,BoardRequest.UpdateDTO requestDTO){
        Board board = boardReposiroty.findById(id);
        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser.getId()!=board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }

        boardReposiroty.updateById(id,requestDTO);
        return "redirect:/board/"+id ;
    }


}
