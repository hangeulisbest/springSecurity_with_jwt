package com.memo.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;
/**
 * BackendApplication 설명 : application.yml 확인해보고 profile 설정하면 됩니다.
 * @author jowonjun
 * @version 1.0.0
 * 작성일 : 2022/01/05
**/
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,"local");
		SpringApplication.run(BackendApplication.class, args);
	}

}
