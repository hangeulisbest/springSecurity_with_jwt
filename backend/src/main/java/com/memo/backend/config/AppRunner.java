package com.memo.backend.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {

    private final Environment env;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("######################################################################");
        log.debug("* spring profile ");
        log.debug("* Enviroment's Active Profile :" + Arrays.toString(env.getActiveProfiles()));
        log.debug("######################################################################");
    }
}
