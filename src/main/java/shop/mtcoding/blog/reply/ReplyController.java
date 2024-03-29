package shop.mtcoding.blog.reply;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Controller
public class ReplyController {
    private final ReplyService replyService ;
    private final HttpSession session;

    @PostMapping("/reply/save")
    public String save(ReplyRequest.SaveDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
         Reply reply = replyService.댓글쓰기(requestDTO,sessionUser);

        return "redirect:/board/"+reply.getBoard().getId() ;
    }

    @PostMapping("/reply/{id}/delete")
    public String delete(@PathVariable("id") Integer id, int boardId){
        User sessionUser = (User) session.getAttribute("sessionUser");
         replyService.댓글삭제(id,sessionUser);

        return "redirect:/board/"+boardId ;
    }

}
