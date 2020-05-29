package br.com.mundodev.scd.api.auth;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.com.mundodev.scd.api.domain.AppUser;
import br.com.mundodev.scd.api.domain.Tomador;
import br.com.mundodev.scd.api.enumeration.AuthenticationStringEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenConfig implements Serializable {
	
	@Value("${app.token.secret-key}")
	private String secretKey;
	
	@Value("${app.token.maximo-horas-ativo}")
	private Long maximoHorasAtivo;
	
	private static final long serialVersionUID = -2550185165626007488L;

	private final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60;

	public String generateToken(final UserDetails userDetails) {
		
		final var claims = new HashMap<String, Object>();
		final var token = generateToken(claims, userDetails.getUsername());

		return token;
	}

	private String generateToken(final Map<String, Object> claims, final String subject) {
		
		final var token = Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + (JWT_TOKEN_VALIDITY * maximoHorasAtivo)))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();

		return token;
	}

	private Claims getAllClaimsFromToken(final String token) {
		try {
			final var claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
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

	public String getSubjectFromToken(final String token) {
		
		final var subject = getClaimFromToken(token, Claims::getSubject);

		return subject;
	}
	
	public String getConvenioFromToken(final String token) {		
		return getAuthenticationDataFromToken(token).get().get(AuthenticationStringEnum.CONVENIO).get();
	}
	
	public String getTomadorFromToken(final String token) {
		return getAuthenticationDataFromToken(token).get().get(AuthenticationStringEnum.TOMADOR).get();
	}
	
	public String getCodigoFromToken(final String token) {
		return getAuthenticationDataFromToken(token).get().get(AuthenticationStringEnum.CODIGO_ACESSO).get();
	}

	public Boolean isTokenValid(final String token, final AppUser userDetails) {
		
		final var authenticationData = getAuthenticationDataFromToken(token);
		
		final var isTokenValid = (
				!isTokenExpired(token) &&
				isOneIdentificationData(authenticationData.get().get(AuthenticationStringEnum.TOMADOR).get(), userDetails.getTomador()) &&
				authenticationData.get().get(AuthenticationStringEnum.CODIGO_ACESSO).get().equals(userDetails.getPassword()) &&
				authenticationData.get().get(AuthenticationStringEnum.CONVENIO).get().equals(userDetails.getTomador().getConvenio().getId().toString())
			);

		return isTokenValid;
	}
	
	private Boolean isOneIdentificationData(final String id, final Tomador tomador) {
		
		if (id == null || tomador == null)
			return Boolean.FALSE;
		
		if (tomador.getCpf() != null && tomador.getCpf().equals(id))
			return Boolean.TRUE;
		
		if (tomador.getCelular() != null && tomador.getCelular().equals(id))
			return Boolean.TRUE;
		
		if (tomador.getEmail() != null && tomador.getEmail().equals(id))
			return Boolean.TRUE;
		
		if (tomador.getId() != null && tomador.getId().toString().equals(id.toString()))
			return Boolean.TRUE;
		
		return Boolean.FALSE;
	}
	
	public Optional<Map<AuthenticationStringEnum, Optional<String>>> getAuthenticationDataFromToken(final String token) {
		
		if (token == null) {
			return Optional.empty();
		}
		
		final var authenticationString = getSubjectFromToken(token);
		
		return getAuthenticationDataFromString(authenticationString);
	}
	
	public Optional<Map<AuthenticationStringEnum, Optional<String>>> getAuthenticationDataFromString(final String authenticationString) {
		
		if (authenticationString == null) {
			return Optional.empty();
		}
		
		final var authenticationStringSplit = authenticationString.split(":");
		
		if (authenticationStringSplit == null) {
			return Optional.empty();
		}
		
		final var map = new HashMap<AuthenticationStringEnum, Optional<String>>();
		
		for (int i = 0; i < authenticationStringSplit.length; i++) {
			if (AuthenticationStringEnum.getFromIndice(i).isPresent()) {
				map.put(AuthenticationStringEnum.getFromIndice(i).get(), Optional.of(authenticationStringSplit[i]));
			}			
		}
		
		return Optional.of(map);
	}
	
}