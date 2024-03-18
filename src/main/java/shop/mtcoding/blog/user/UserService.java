package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.err.exception.Exception400;
import shop.mtcoding.blog._core.err.exception.Exception401;
import shop.mtcoding.blog._core.err.exception.Exception404;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJPARepository userJPARepository ;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO requestDTO) {
       Optional<User> userOP = userJPARepository.findByUsername(requestDTO.getUsername());
       if(userOP.isPresent()){
           throw new Exception400("중복된 유저네임입니다.");
       }
       userJPARepository.save(requestDTO.toEntity());
    }


    public User 로그인(UserRequest.LoginDTO requestDTO) {
        User sessionUser = userJPARepository.findByUsernameAndPassword(requestDTO.getUsername(),requestDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다."));

        return sessionUser;
    }

    public User 회원수정폼(int userId) {
       User user = userJPARepository.findById(userId).orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다."));
       return user;
    }

    public User 회원수정(UserRequest.UpdateDTO requestDTO, int id) {
        User user =userJPARepository.findById(id).orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다."));
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());
        return user;

    }
}
