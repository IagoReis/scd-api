package br.com.mundodev.scd.api.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenConfig implements Serializable {
	private static final long serialVersionUID = -2550185165626007488L;

	// Validade do Token
	public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * 6; // 06 horas

	// Secret Key
	private static final String SECRET_KEY = "o^kBbwdd&29o$qmmJJ@7ad399yEKVYaAXp7!";

	public String generateToken(final UserDetails userDetails) {
		
		final var claims = new HashMap<String, Object>();
		final var token = generateToken(claims, userDetails.getUsername());

		return token;
	}

	private String generateToken(final Map<String, Object> claims, final String subject) {
		
		final var token = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();

		return token;
	}

	private Claims getAllClaimsFromToken(final String token) {
		try {
			final var claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
			return claims;
		}
		catch (ExpiredJwtException e) {
			return null;
		}
	}

	public <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
		
		final var claims = getAllClaimsFromToken(token);

		if (claims == null) {
			return null;
		}

		final var claim = claimsResolver.apply(claims);

		return claim;
	}

	public Date getExpirationDateFromToken(final String token) {
		
		final var expirationDate = getClaimFromToken(token, Claims::getExpiration);

		return expirationDate;
	}

	private Boolean isTokenExpired(final String token) {
		
		final var expirationDate = getExpirationDateFromToken(token);

		if (expirationDate == null) {
			return Boolean.TRUE;
		}

		final var isTokenExpired = expirationDate.before(new Date());

		return isTokenExpired;
	}

	public String getUsernameFromToken(final String token) {
		
		final var username = getClaimFromToken(token, Claims::getSubject);

		return username;
	}

	public Boolean isTokenValid(final String token, final UserDetails userDetails) {
		
		final var username = getUsernameFromToken(token);
		final var isTokenValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

		return isTokenValid;
	}
}