package com.zl.gulimall.gulimallseckill.interceptor;

import com.zl.common.constant.AuthServerConstant;
import com.zl.common.vo.MemberRespVo;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ZhuLing
 * @date 2021/11/27 - 21:31
 */
@Component
public class LoginUserInterceptor implements HandlerInterceptor {
    public  static ThreadLocal<MemberRespVo>  loginUser=new ThreadLocal<>();

    @Override
    public  boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match("/kill", uri);

        if (match) {
            HttpSession session = request.getSession();
            MemberRespVo attribute = (MemberRespVo) session.getAttribute(AuthServerConstant.LOGIN_USER);
            if (attribute!=null){
                loginUser.set(attribute);
                return true;
            }else {
                request.getSession().setAttribute("msg","请先登录");
                response.sendRedirect("http://auth.gulimall.com/login.html");
                return false;
            }
        }
        return true;
    }
}
