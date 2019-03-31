package com.moebius.backend.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfiguration {

	@Bean
	SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
		return http
			.csrf().disable()
            .formLogin().disable()
			.authorizeExchange()
			.pathMatchers("/",
					"/login",
					"/static/**").permitAll()
			.pathMatchers("/admin").hasAuthority("ADMIN")
			.anyExchange().authenticated()
			.and().build();
	}

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
