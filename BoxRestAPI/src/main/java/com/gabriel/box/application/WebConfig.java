package com.gabriel.box.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication()
				.withUser("user").password("{noop}userPassword").roles("CLIENT")
				.and()
				.withUser("box").password("{noop}boxPassword").roles("BOX")
				.and()
				.withUser("admin").password("{noop}adminBolado").roles("BOX", "CLIENT", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic()
        	.and()
        	.authorizeRequests()
        	.antMatchers(HttpMethod.GET, "/client/**").hasRole("CLIENT")
        	.antMatchers(HttpMethod.POST, "/client").hasRole("CLIENT")
        	.antMatchers(HttpMethod.PUT, "/client/**").hasRole("CLIENT")
        	.antMatchers(HttpMethod.PATCH, "/client/**").hasRole("CLIENT")
        	.antMatchers(HttpMethod.DELETE, "/client/**").hasRole("CLIENT")
        	.antMatchers(HttpMethod.GET, "/box/**").hasRole("BOX")
        	.antMatchers(HttpMethod.POST, "/box").hasRole("BOX")
        	.antMatchers(HttpMethod.PUT, "/box/**").hasRole("BOX")
        	.antMatchers(HttpMethod.PATCH, "/box/**").hasRole("BOX")
        	.antMatchers(HttpMethod.DELETE, "/box/**").hasRole("BOX")
        	.and()
        	.csrf().disable()
        	.formLogin().disable();
	}

}
