package shop.mtcoding.blog.board;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.err.exception.Exception403;
import shop.mtcoding.blog._core.err.exception.Exception404;
import shop.mtcoding.blog.user.User;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJPARepository boardJPARepository ;

    @Transactional
    public  void 글쓰기(BoardRequest.SaveDTO requestDTO, User sessionUser){
        boardJPARepository.save(requestDTO.toEntity(sessionUser));
    }

    @Transactional
    public void 글조회(int boardId,int sessionUserId,BoardRequest.UpdateDTO requestDTO){
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

    @Transactional
    public void 글삭제(int boardId, int sessionUserId) {
        Board board = boardJPARepository.findById(boardId).orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        if(sessionUserId!=board.getUser().getId()){
            throw new Exception403("게시글을 삭제할 권한이 없습니다");
        }// 트랜잭션은 런타임익셉션이 발동하면 롤백된다.
        boardJPARepository.deleteById(boardId);

    }

    public List<Board> 글목록조회() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
       List<Board> boardList = boardJPARepository.findAll(sort);
       return boardList;
    }

    public BoardResponse.DetailDTO  글상세보기(Integer id, User sessionUser) {
        Board board = boardJPARepository.findById(id).orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        // 수정삭제버튼 권한부여
        // 상세보기는 board 와 owner  두개를 리턴해야 됨. 메서드는 하나의 타입만 리턴 가능하기 때문에 리턴 타입을 담을 그릇이 필요하다.

        return new BoardResponse.DetailDTO(board,sessionUser);

    }
}
