package com.memo.backend.config;

import com.memo.backend.argumentresolver.LoginMemberArgumentResolver;
import com.memo.backend.filter.login.LoginCheckFilter;
import com.memo.backend.filter.login.LoginLogFilter;
import com.memo.backend.interceptor.LogInterceptor;
import com.memo.backend.interceptor.LoginCheckInterceptor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.List;

/**
 * AppConfig 설명 : App Config 를 위한 빈정보 관리
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/07
**/
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new LoginMemberArgumentResolver());
    }

    // 인터셉터 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그 남기는 인터셉터
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**");
                //.excludePathPatterns("/css/*")

        // 로그인 체크하는 인터셉터
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/member/v1",
                        "/api/login/**",
                        "/api/logout/**",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/api-docs",
                        "/error");
    }

    // DTO , VO 간 매퍼 관리
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }


    // Filter 관리
/*    @Bean
    public FilterRegistrationBean<Filter> logFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginLogFilter());
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setOrder(1);
        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> checkFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setOrder(2);
        return filterFilterRegistrationBean;
    }*/

}
