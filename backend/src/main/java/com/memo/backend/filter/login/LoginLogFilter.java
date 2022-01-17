package com.memo.backend.filter.login;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;
/**
 * LoginLogFilter 설명 :
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/08
 * NOT USED
**/
@Slf4j
public class LoginLogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        UUID logID = UUID.randomUUID();
        String requestURI = servletRequest.getRequestURI();
        String method = servletRequest.getMethod();
        try {
            log.info("REQUEST [{}][{}]- {}",method,requestURI,logID);
            chain.doFilter(request,response);
        }catch (Exception e){
            log.error("log filter error = {}",e.getMessage());
            throw e;
        }finally {
            log.info("RESPONSE [{}][{}]- {}",method,requestURI,logID);
        }

    }
}
