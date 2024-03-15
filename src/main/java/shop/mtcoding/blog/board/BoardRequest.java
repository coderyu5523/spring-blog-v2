package shop.mtcoding.blog.board;


import lombok.Data;
import shop.mtcoding.blog.user.User;

public class BoardRequest {

    @Data
    public static class SaveDTO {
        private String title;
        private String content;

        //DTO를 클라이언트로부터 받아서 영속성 컨텍스트에 전달하기 위해 사용
        //인서트 하는 DTO에서만 만든다.
        public Board toEntity(User user) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)  //user id 가 아니라 user 객체를 넣으면 됨
                    .build();

        }

    }
    @Data
    public static class UpdateDTO{
        private String title;
        private String content;

    }

}
