package shop.mtcoding.blog.board;

import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardResponse {
    @Data
    public static class DetailDTO{
        private int id;
        private String title;
        private String content;
        private UserDTO user ;
        private Boolean owner ;


        public DetailDTO(Board board,User sessionUser) {
            this.id = board.getId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.user = new UserDTO(board.getUser());
            this.owner = sessionUser != null && sessionUser.getId() == board.getUser().getId();
            ;

        }

        // 수정삭제버튼 권한부여
        public class UserDTO{
            private int id ;
            private String username;

                public UserDTO(User user) {
                this.id = user.getId();
                this.username = user.getUsername();
            }
        }
    }
}
