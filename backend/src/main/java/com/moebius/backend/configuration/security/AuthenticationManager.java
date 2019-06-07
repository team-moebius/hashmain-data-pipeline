package com.moebius.backend.configuration.security;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Override
    // FIXME : Need to refactor these code as chained one
    public Mono<Authentication> authenticate(Authentication authentication) {

        String authToken = authentication.getCredentials().toString();

        Claims claims;
        try {
            claims = JwtUtil.getAllClaimsFromToken(authToken);
        } catch (Exception e) {
            claims = null;
        }
        if (claims != null && !JwtUtil.isTokenExpired(claims)) {
            String userName = claims.getSubject();
            List<String> roles = claims.get("roles", List.class);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                userName,
                authToken,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet())
            );
            return Mono.just(auth);
        } else {
            return Mono.empty();
        }
    }
}
