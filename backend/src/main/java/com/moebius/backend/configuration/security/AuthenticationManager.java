package com.moebius.backend.configuration.security;

import com.moebius.backend.domain.members.Role;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
	private static final String AUTHORITIES_KEY = "roles";

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		return Mono.fromCallable(() -> {
			String authToken = authentication.getCredentials().toString();
			Claims claims;

			try {
				claims = JwtUtil.getAllClaimsFromToken(authToken);
			} catch (Exception e) {
				log.warn("There is an exception on getAllClaimsFromToken.", e);
				claims = null;
			}
			if (claims != null && !JwtUtil.isTokenExpired(claims)) {
				String userName = claims.getSubject();
				List<String> rawRoles = claims.get(AUTHORITIES_KEY, List.class);
				Set<Role> roles = rawRoles.stream().map(Role::new).collect(Collectors.toSet());
				return (Authentication) new UsernamePasswordAuthenticationToken(userName, authToken, roles);
			}
			return null;
		}).subscribeOn(COMPUTE.scheduler());
	}
}
