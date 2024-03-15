package shop.mtcoding.blog.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository ;
    private final HttpSession session;

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO){
       User sessionUser = userRepository.save(requestDTO.toEntity());
        session.setAttribute("sessionUser",sessionUser);
        //회원가입 후 자동 로그인되게
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO){

       User sessionUser = userRepository.findByUsernameAndPassword(requestDTO);
       session.setAttribute("sessionUser",sessionUser);
       return "redirect:/";
    }

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login-form";
    }

    @GetMapping("/user/update-form")
    public String updateForm(HttpServletRequest request) {
        User sessionUser = (User) session.getAttribute("sessionUser");
      User user =  userRepository.findById(sessionUser.getId());
        request.setAttribute("user",user);

        return "user/update-form";
    }
    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userRepository.updateById(sessionUser.getId(),requestDTO);
        session.setAttribute("sessionUser",user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
