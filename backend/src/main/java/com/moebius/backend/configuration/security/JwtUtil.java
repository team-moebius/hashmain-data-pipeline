package com.moebius.backend.configuration.security;

import com.moebius.backend.model.MoebiusPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.io.Serializable;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil implements Serializable {
	private static final long serialVersionUID = 7286015171049934299L;

	private static final String ISSUER = "MOEBIUS";
	private static final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private static final long expirationTime = 300000L;

	public static String generateToken(MoebiusPrincipal principal) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", principal.getAuthorities());

		Date createdAt = new Date();
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(principal.getUsername())
			.setIssuedAt(createdAt)
			.setIssuer(ISSUER)
			.setExpiration(new Date(createdAt.getTime() + expirationTime))
			.signWith(secret)
			.compact();
	}

	static Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
			.setSigningKey(Base64.getEncoder().encodeToString(secret.getEncoded()))
			.parseClaimsJws(token)
			.getBody();
	}

	static Boolean isTokenExpired(Claims claims) {
		return claims.getExpiration().before(new Date());
	}
}
