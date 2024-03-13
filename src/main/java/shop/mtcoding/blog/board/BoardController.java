package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class BoardController {


    @PostMapping("/board/save")
    public String save(){ //DTO 없이 데이터 받음


        return "redirect:/";
    }

    @GetMapping({ "/"})
    public String index() {
        return "index"; // 리퀘스트디스패쳐 방식으로 가방을 내부적으로 전달.
    }

    @GetMapping("/board/save-form")
    public String saveForm() {
        return "board/save-form";
    }

    @GetMapping("/board/{id}")
    public String detail(@PathVariable Integer id,HttpServletRequest request) {  // int 를 쓰면 값이 없으면 0, Integer 를 넣으면 값이 없을 때 null 값이 들어옴.


        return "board/detail";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable Integer id){


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
