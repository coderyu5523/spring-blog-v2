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

    private final UserRepository userRepository ;
    private final HttpSession session;

    @PostMapping("/join")
    public String join(UserRequest.JoinDTO requestDTO){

        try {
            User sessionUser = userRepository.save(requestDTO.toEntity());
            session.setAttribute("sessionUser",sessionUser);

        } catch (Exception e) {
            throw new Exception400("동일한 아이디가 존재합니다.");
        }
        //회원가입 후 자동 로그인되게
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(UserRequest.LoginDTO requestDTO){

        try {
         User sessionUser = userRepository.findByUsernameAndPassword(requestDTO);
            session.setAttribute("sessionUser",sessionUser);
        } catch (Exception e) {
            throw new Exception401("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
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
