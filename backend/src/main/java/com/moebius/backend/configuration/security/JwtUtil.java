package com.moebius.backend.configuration.security;

import com.moebius.backend.domain.members.MoebiusPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil implements Serializable {
	private static final long serialVersionUID = 7286015171049934299L;

	private static final String ISSUER = "moebius";
	private static final String AUTHORITIES_KEY = "roles";
	private static final Key SECRET = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private static final long EXPIRATION_TIME = 60000L * 60L * 12L; // 12 hours

	public static String generateToken(MoebiusPrincipal principal) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(AUTHORITIES_KEY, principal.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.toSet()));

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

	static Boolean isTokenExpired(Claims claims) {
		return claims.getExpiration().before(new Date());
	}
}
