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
			if (claims != null && !JwtUtil.isTokenExpired(claims) && JwtUtil.isActiveMember(claims)) {
				String memberId = claims.getSubject();
				List<String> rawRoles = JwtUtil.getRoles(claims);
				Set<Role> roles = rawRoles.stream().map(Role::new).collect(Collectors.toSet());
				return (Authentication) new UsernamePasswordAuthenticationToken(memberId, authToken, roles);
			}
			return null;
		}).subscribeOn(COMPUTE.scheduler());
	}
}
