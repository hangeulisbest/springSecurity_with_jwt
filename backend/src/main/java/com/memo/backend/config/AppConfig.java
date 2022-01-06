package com.memo.backend.config;

import com.memo.backend.filter.login.LoginLogFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * AppConfig 설명 : App Config 를 위한 빈정보 관리
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/07
**/
@Configuration
public class AppConfig {

    // DTO , VO 간 매퍼 관리
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return modelMapper;
    }

    // Filter 관리

    @Bean
    public FilterRegistrationBean<Filter> filter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginLogFilter());
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setOrder(1);
        return filterFilterRegistrationBean;
    }


}
