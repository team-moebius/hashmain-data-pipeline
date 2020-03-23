package com.moebius.backend.configuration.security;

import com.moebius.backend.domain.members.Role;
import com.moebius.backend.exception.DataNotVerifiedException;
import com.moebius.backend.exception.ExceptionTypes;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String authToken = authentication.getCredentials().toString();

		try {
			Claims claims = JwtUtil.getAllClaimsFromToken(authToken);

			if (claims != null && !JwtUtil.isTokenExpired(claims) && JwtUtil.isActiveMember(claims)) {
				String memberId = claims.getSubject();
				List<String> rawRoles = JwtUtil.getRoles(claims);
				Set<Role> roles = rawRoles.stream().map(Role::new).collect(Collectors.toSet());
				return Mono.just(new UsernamePasswordAuthenticationToken(memberId, authToken, roles));
			}
		} catch (Exception e) {
			log.warn("There is an exception on getAllClaimsFromToken.", e);
		}
		return Mono.defer(() -> Mono.error(new DataNotVerifiedException(ExceptionTypes.UNVERIFIED_DATA.getMessage(authToken))));
	}
}
