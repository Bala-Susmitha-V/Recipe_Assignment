package com.abn.amro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
/**
 * This is main class where we run our application and excluded default security configuration.
 * @author Bala Susmitha Vinjamuri
 *
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SecurityScheme(name = "recipeapi", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class AmroApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmroApplication.class, args);
	}

}
