package shop.mtcoding.blog._core.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.sqm.mutation.internal.Handler;
import org.springframework.web.servlet.HandlerInterceptor;
import shop.mtcoding.blog._core.err.exception.Exception401;
import shop.mtcoding.blog.user.User;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandler,,,,,,,,");
        HttpSession session = request.getSession();

        User sessionUser = (User) session.getAttribute("sessionUser");
        if(sessionUser ==null){
            throw new Exception401("로그인이 필요합니다.");
        }
        return true;
    }
}
