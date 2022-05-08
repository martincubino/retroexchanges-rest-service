package com.retroexchanges.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import com.retroexchanges.rest.security.RetroexchangesAuthorizationFilter;


@SpringBootApplication
@EnableJpaAuditing
public class RetroexchangesRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetroexchangesRestApplication.class, args);
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable()
				.addFilterAfter(new RetroexchangesAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/category/*").permitAll()
				.antMatchers(HttpMethod.GET, "/api/product/*").permitAll()
				.antMatchers(HttpMethod.GET, "/api/products").permitAll()
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()
				.antMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/logout").hasRole("USER")
				.antMatchers(HttpMethod.POST, "/api/register").permitAll()
				.antMatchers(HttpMethod.GET, "/api/categories").permitAll()
				
				.anyRequest().authenticated();
		}
	
	
	    /*@Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.cors().and().csrf().disable();
	    }*/
	
	    
	}
}
