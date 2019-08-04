package com.moebius.backend.configuration;

import com.moebius.backend.configuration.security.AuthenticationManager;
import com.moebius.backend.configuration.security.SecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {
	private final AuthenticationManager authenticationManager;
	private final SecurityContextRepository securityContextRepository;

	@Bean
	SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
		return http
			.csrf().disable()
			.formLogin().disable()
			.authenticationManager(authenticationManager)
			.securityContextRepository(securityContextRepository)
			.authorizeExchange()
			.pathMatchers(
				"/",
				"/csrf",
				"/v2/api-docs",
				"/swagger",
				"/swagger-ui.html",
				"/swagger-resources/**",
				"/webjars/**",
				"/api/member/**",
				"/api/members/**", // TODO : Find out proper way to reduce duplicate patterns
				"/login",
				"/static/**").permitAll()
			.pathMatchers("/admin").hasAuthority("ADMIN")
			.pathMatchers("/api/stoplosses/**").hasAuthority("MEMBER")
			.anyExchange().authenticated()
			.and()
			.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		Pbkdf2PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder();
		passwordEncoder.setEncodeHashAsBase64(true);
		passwordEncoder.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
		return passwordEncoder;
	}

}
