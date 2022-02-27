package com.memo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    /**
     *
     * 2022-02-26
     *
     * @return PasswordEncoder - Spring5 부터 encoder에 {id} 값을 붙이고 가져와야 비교가 가능하다.
     * 예를 들어, 어떤 Member의 비밀번호가 1234이고 이를 인코딩하여 "ABS@DS" 로 저장했을때
     *   passwordEncoder.matches("1234", {noop}ABS@DS )) 로 해야 비교가 가능하다.
     *   처음에는 SecurityConfig 에 빈으로 생성하였으나 SecurityConfig 에서 CustomEmailPasswordAuthProvider 를 의존하고
     *   CustomEmailPasswordAuthProvider 는 passwordEncoder(SecurityConfig)에 의존하여 순환참조가 일어나
     *   따로 빈을 만드는 AppConfig를 생성하였다.
     *
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // noop 사용
    }
}
