package com.memo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger3Config 설명 : swagger3 API 문서를 만들때 설정
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/04
**/
@Configuration
public class Swagger3Config {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.memo")) // API 문서를 만들어줄 범위
                .paths(PathSelectors.any()) // ex PathSelectors.ant("api/v1/**") 으로 한다면 해당경로 하위의 API에 대해서만 문서화한다.
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Wine API")
                .version("1.0.0")
                .description("Wine API 문서에 오신것을 환영합니다.")
                .license("No license")
                .licenseUrl("license url not found")
                .build();
    }
}
