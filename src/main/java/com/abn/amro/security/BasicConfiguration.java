package com.abn.amro.security;

import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * This class provides the authentication for rest apis
 * @author Bala Susmitha Vinjamuri
 *
 */
@EnableWebSecurity
public class BasicConfiguration extends WebSecurityConfigurerAdapter {

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
 
}