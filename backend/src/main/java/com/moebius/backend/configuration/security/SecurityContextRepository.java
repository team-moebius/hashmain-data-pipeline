package com.moebius.backend.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private static final String AUTH_PREFIX = "Bearer";
    private static final int TOKEN_STARTING_INDEX = 7;

    private final AuthenticationManager authenticationManager;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return null;
    }

    @Override
//     FIXME : Need to refactor these code as chained one
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith(AUTH_PREFIX)) {
            String authToken = authHeader.substring(TOKEN_STARTING_INDEX);
            // TODO : Need to check out parameters in constructor (maybe need to be refactored)
            Authentication auth = new UsernamePasswordAuthenticationToken(request.getId(), authToken);
            return authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
        }
        return Mono.empty();
    }
}
