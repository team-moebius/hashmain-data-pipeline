package com.moebius.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

	@Bean
	SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
		return http
			.csrf().disable()
			.formLogin().disable()
			.authorizeExchange()
			.pathMatchers("/",
				"/swagger-ui.html",
				"/v2/api-docs",
				"/login",
				"/static/**").permitAll()
			.pathMatchers("/admin").hasAuthority("ADMIN")
			.anyExchange().authenticated()
			.and().build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
		passwordEncoder.setEncodeHashAsBase64(true);
		passwordEncoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
		return passwordEncoder;
	}
}
