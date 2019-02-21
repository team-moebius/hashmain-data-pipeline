package com.moebius.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

	@Bean
	public MapReactiveUserDetailsService userDetailsService() {
		UserDetails user = User
			.withUsername("user")
			.password(new BCryptPasswordEncoder().encode("password"))
			.roles("USER")
			.build();
		return new MapReactiveUserDetailsService(user);
	}
}
