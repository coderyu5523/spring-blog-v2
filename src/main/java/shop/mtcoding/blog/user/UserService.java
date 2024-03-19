package shop.mtcoding.blog.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.err.exception.Exception400;
import shop.mtcoding.blog._core.err.exception.Exception401;
import shop.mtcoding.blog._core.err.exception.Exception404;

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

    public User 로그인(UserRequest.LoginDTO requestDTO){
     User sessionUser = userJPARepository.findByUsernameAndPassword(requestDTO.getUsername(),requestDTO.getPassword())
                .orElseThrow(() -> new Exception401("인증되지 않았습니다.")); // 조회했을 때 값이 NULL 일때 THROW 를 날림

        return sessionUser ;
    }
    public User 회원수정폼(int boardId){
        //예외처리
       User user = userJPARepository.findById(boardId).orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다."));

         return user;
    }
    @Transactional
    public User 회원조회(int id,UserRequest.UpdateDTO requestDTO){
        User user = userJPARepository.findById(id)
                .orElseThrow(() -> new Exception404("회원정보를 찾을 수 없습니다."));
        user.setPassword(requestDTO.getPassword());
        user.setEmail(requestDTO.getEmail());
        return user ;
    }
}

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
