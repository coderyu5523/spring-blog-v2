package shop.mtcoding.blog._core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import shop.mtcoding.blog._core.err.exception.Exception403;
import shop.mtcoding.blog.board.Board;
import shop.mtcoding.blog.user.User;

public class ForbiddenInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("sessionUser");
        Board board = (Board) request.getAttribute("board");
        if (sessionUser.getId()!=board.getUser().getId()){
            throw new Exception403("권한이 없습니다");
        }
    }
}
