package com.memo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.List;

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
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
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

    /**
     * securityContext 설명 :  스웨거 등록
     * @author jowonjun
     * @version 1.0.0
     * 작성일 : 2022/01/27
    **/
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("JWT", authorizationScopes));
    }

    /**
     * apiKey 설명 : jwt 를 활용한 인증방식 설정을 위한 Api key
     * @author jowonjun
     * @version 1.0.0
     * 작성일 : 2022/01/25
    **/
    private ApiKey apiKey() {
        return new ApiKey("JWT","Authorization","header");
    }
}
