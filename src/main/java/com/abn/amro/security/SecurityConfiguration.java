package com.abn.amro.security;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This class provides the authentication for rest apis in swagger
 * @author Bala Susmitha Vinjamuri
 *
 */
@EnableWebSecurity
@EnableSwagger2
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * Sets the credentials for USER and ADMIN roles
	 */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	PasswordEncoder encoder = 
          PasswordEncoderFactories.createDelegatingPasswordEncoder();
    	auth
          .inMemoryAuthentication()
          .withUser("user")
          .password(encoder.encode("password"))
          .roles("USER")
          .and()
          .withUser("admin")
          .password(encoder.encode("admin"))
          .roles("USER", "ADMIN");
    }

    /**
     * Provides the security for recipe restend points and allows h2db for all.
     * Throws Forbidden error for any other pages
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable()
    	.authorizeRequests()
		.antMatchers("/h2-console/**","/v2/api-docs", "/v3/api-docs","configuration/**",
	            "/swagger-resources/**", 
	            "/swagger-ui/**").permitAll();
    	http.httpBasic().and().authorizeRequests().antMatchers("/recipe/**")
		.hasRole("USER").antMatchers("/**").hasRole("ADMIN");	
    	http.headers().frameOptions().disable();
    	
    	   
        
    }
    
    
    /**
	 * displays webpage description
	 * @return apiinfo
	 */
    private ApiInfo apiInfo() {
        return new ApiInfo("Recipe App Rest APIs",
                "APIs for RecipeApp.",
                "1.0",
                "Terms of service",
                new Contact("test", "susmitha", "bala.vinjamuri@capgemini.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }
    
    /**
     * Builds api documentation type on base package
     * @return Docket
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.abn.amro"))
                .paths(regex("/recipe.*"))
                .build();
    }
 
}