package shop.mtcoding.blog.user;

import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import shop.mtcoding.blog._core.err.exception.Exception400;
import shop.mtcoding.blog._core.err.exception.Exception401;

import java.net.http.HttpRequest;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService ;
    private final UserRepository userRepository ;
    private final HttpSession session;

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO){

        userService.회원가입(requestDTO);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO){

        userService.로그인(requestDTO);

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

       User user = userService.회원수정폼(sessionUser.getId());

        request.setAttribute("user",user);

        return "user/update-form";
    }
    @PostMapping("/user/update")
    public String update(UserRequest.UpdateDTO requestDTO){
        User sessionUser = (User) session.getAttribute("sessionUser");
        User user = userService.회원수정(requestDTO,sessionUser.getId());
        session.setAttribute("sessionUser",user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }
}
