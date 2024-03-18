package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.err.exception.Exception400;

import java.util.Optional;

@RequiredArgsConstructor
@Service  //ioc 등록
public class UserService {
    private final UserJPARepository userJPARepository ;

    @Transactional
    public void 회원가입(UserRequest.JoinDTO requestDTO){
        //1.유효성검사(컨트롤러 책임)

        //2. 중복검사
        Optional<User> userOP =  userJPARepository.findByUsername(requestDTO.getUsername());
        if(userOP.isPresent()){  //아이디가 중복된다면
            throw new Exception400("중복된 유저네임입니다.");
        }

        userJPARepository.save(requestDTO.toEntity());

    }
}
