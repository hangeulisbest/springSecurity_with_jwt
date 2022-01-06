package com.memo.backend.filter.login;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Slf4j
public class LoginLogFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LoginLogFilter INIT");
    }

    @Override
    public void destroy() {
        log.info("log filter DESTROY !");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        UUID logID = UUID.randomUUID();
        String requestURI = servletRequest.getRequestURI();

        try {
            log.info("REQUEST [{}][{}][{}]",logID,requestURI,new Date());
            chain.doFilter(request,response);
        }catch (Exception e){
            throw e;
        }finally {
            log.info("RESPONSE [{}][{}][{}]",logID,requestURI,new Date());
        }
    }
}
