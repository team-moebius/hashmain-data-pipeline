package com.moebius.backend.configuration.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    // FIXME : Need to refactor these code as chained one
    public Mono<Authentication> authenticate(Authentication authentication) {

        String authToken = authentication.getCredentials().toString();

        String username;
        try {
            username = JwtUtil.getUsernameFromToken(authToken);
        } catch (Exception e) {
            username = null;
        }
        if (username != null && JwtUtil.isTokenExpired(authToken)) {
            Claims claims = JwtUtil.getAllClaimsFromToken(authToken);
            Set<String> roles = claims.get("role", Set.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                username,
                authToken,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
