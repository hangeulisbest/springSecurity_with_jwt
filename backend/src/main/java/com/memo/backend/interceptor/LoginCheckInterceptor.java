package com.memo.backend.interceptor;

import com.memo.backend.commoncode.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String)request.getAttribute(LOG_ID);
        if(handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            log.info("LoginCheckInterceptor PREHANDLE ID : [{}] URI : [{}][{}] HANDLER : [{}]", uuid, request.getMethod(), requestURI, hm);
        }
        // false 를 안하면 세션을 생성해버림
        HttpSession session = request.getSession(false);

        // 미인증 사용자
        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER)==null){
            log.info("LoginCheckInterceptor PREHANDLE ID : [{}] 미인증 사용자 요청 발생!",uuid);
            /*
             * sendRedirect를 통해서 login 페이지로 이동후 redirectURL을 이용해 원래 접근하려던 페이지로 이동할 수 있어야한다.
             * servletResponse.sendRedirect("/login?redirectURL="+requestURI);
             * */
            response.sendError(401);
            return false;
        }

        return true;
    }
}
