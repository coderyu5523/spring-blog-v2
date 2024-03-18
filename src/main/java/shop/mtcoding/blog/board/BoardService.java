package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.err.exception.Exception403;
import shop.mtcoding.blog._core.err.exception.Exception404;
import shop.mtcoding.blog.user.User;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository ;

    @Transactional
    public  void 글쓰기(BoardRequest.SaveDTO requestDTO, User sessionUser){
        boardJPARepository.save(requestDTO.toEntity(sessionUser));
    }

    @Transactional
    public void 글수정(int boardId,int sessionUserId,BoardRequest.UpdateDTO requestDTO){
        //조회 및 예외처리
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        //권한 처리,
        if(sessionUserId!=board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }

        board.setTitle(requestDTO.getTitle());
        board.setContent((requestDTO.getContent()));
    }

    public Board 글수정폼(int boardId,int sessionUserId){
        Board board = boardJPARepository.findById(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        if(sessionUserId!=board.getUser().getId()){
            throw new Exception403("게시글을 수정할 권한이 없습니다");
        }

        return board ;
    }
}
