package com.moebius.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

	@Bean
	SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
		return http
			.csrf().disable()
			.authorizeExchange()
			.pathMatchers("/login", "/logout").permitAll()
			.pathMatchers("/admin").hasAuthority("ADMIN")
//			.pathMatchers("/i18n/**",
//				"/css/**",
//				"/fonts/**",
//				"/icons-reference/**",
//				"/img/**",
//				"/js/**",
//				"/vendor/**").permitAll()
			.anyExchange()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.and()
			.logout()
			.logoutUrl("/logout")
			.and()
			.build();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
