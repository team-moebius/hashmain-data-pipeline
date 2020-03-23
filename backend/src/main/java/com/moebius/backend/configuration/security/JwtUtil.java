package com.moebius.backend.configuration.security;

import com.moebius.backend.domain.members.MoebiusPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil implements Serializable {
	private static final long serialVersionUID = 7286015171049934299L;

	private static final String AUTHORITY_KEY = "roles";
	private static final String ISSUER = "moebius";
	private static final String VERIFICATION_KEY = "isActive";
	private static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private static final long EXPIRATION_TIME = 60000L * 1L; // 1 minutes

	public static String generateToken(MoebiusPrincipal principal) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(AUTHORITY_KEY, principal.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toSet()));
		claims.put(VERIFICATION_KEY, principal.isEnabled());

		Date createdAt = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(principal.getUsername())
			.setIssuedAt(createdAt)
			.setIssuer(ISSUER)
			.setExpiration(new Date(createdAt.getTime() + EXPIRATION_TIME))
			.signWith(SECRET)
			.compact();
	}

	static Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
			.setSigningKey(Base64.getEncoder().encodeToString(SECRET.getEncoded()))
			.parseClaimsJws(token)
			.getBody();
	}

	static boolean isTokenExpired(Claims claims) {
		return claims.getExpiration().before(new Date());
	}

	static boolean isActiveMember(Claims claims) {
		return claims.get(VERIFICATION_KEY, Boolean.class);
	}

	static List<String> getRoles(Claims claims) {
		return claims.get(AUTHORITY_KEY, List.class);
	}
}
