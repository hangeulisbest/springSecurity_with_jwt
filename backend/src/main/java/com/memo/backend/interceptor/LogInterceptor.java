package com.memo.backend.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {


    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        // aftercompletion에 넘기는 방법
        request.setAttribute(LOG_ID,uuid);

        // @RequestMapping 인경우 handler : HandlerMethod (컨트롤러의 모든 정보가 담겨져 있다)
        // 정적리소스 : ResourceHttpRequestHandler
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            log.info("LogInterceptor PREHANDLE ID : [{}] URI : [{}][{}] HANDLER : [{}]",uuid,request.getMethod(),requestURI,hm);
            //log.debug("호출할 메서드 이름 = {}",hm.getMethod().getName());
        }
        return true;
    }


    /**
     * postHandle 설명 :  controller에서 에러발생시 호출되지 않는다
     * @author jowonjun
     * @version 1.0.0
     * 작성일 : 2022/01/07
    **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("LogInterceptor POSTHANDLE modelAndView = {}",modelAndView);
    }

    // 컨트롤러에서 에러가 발생해도 실행된다.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);

        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            log.info("LogInterceptor AFTERCOMPLETION ID : [{}] URI : [{}][{}] HANDLER : [{}]",logId,request.getMethod(),requestURI,hm);
            //log.debug("호출할 메서드 이름 = {}",hm.getMethod().getName());
        }

        if(ex != null){
            log.error("LogInterceptor AFTERCOMPLETION ERROR = {}",ex.toString());
        }
    }
}
