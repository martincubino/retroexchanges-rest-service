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
					.antMatchers(HttpMethod.POST, "/api/product").hasRole("USER")
					.antMatchers(HttpMethod.POST, "/api/favorite").hasRole("USER")
					.antMatchers(HttpMethod.DELETE, "/api/favorite").hasRole("USER")
					.antMatchers(HttpMethod.GET, "/api/favorite").hasRole("USER")
					.antMatchers(HttpMethod.GET, "/api/favorite/*").hasRole("USER")
					.antMatchers(HttpMethod.GET, "/api/favorites").hasRole("USER")
					.antMatchers(HttpMethod.PUT, "/api/product").hasRole("USER")
					.antMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT, "/api/category").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST, "/api/category").hasRole("ADMIN")
					.antMatchers(HttpMethod.GET, "/api/user").hasRole("USER")
					.antMatchers(HttpMethod.GET, "/api/product/*").permitAll()
					.antMatchers(HttpMethod.GET, "/api/products").permitAll()
					.antMatchers(HttpMethod.GET, "/api/products/user/*").permitAll()
					.antMatchers(HttpMethod.GET, "/api/products/category/*").permitAll()
					.antMatchers(HttpMethod.GET, "/api/products/name/*").permitAll()
					.antMatchers(HttpMethod.POST, "/api/register").permitAll()
					.antMatchers(HttpMethod.GET, "/api/categories").permitAll()
					.antMatchers(HttpMethod.GET, "/api/category/*").permitAll()
					.antMatchers(HttpMethod.POST, "/api/login").permitAll()
					.anyRequest().authenticated();
		}

		/*
		 * @Override protected void configure(HttpSecurity http) throws Exception {
		 * http.cors().and().csrf().disable(); }
		 */

	}
}
