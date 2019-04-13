package com.moebius.backend.configuration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Base64;

@Component
public class JwtUtil implements Serializable {
	private static final long serialVersionUID = 7286015171049934299L;

	private static final String secret = "TEST_KEY";
	private static final long expirationTime = 28800L;

	Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes())).parseClaimsJws(token).getBody();
	}


}
