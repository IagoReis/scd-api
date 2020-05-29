package br.com.mundodev.scd.api.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	private AppUserDetailsService appUserDetailsService;

	private JwtTokenConfig jwtTokenConfig;

	@Autowired
	public JwtRequestFilter(final AppUserDetailsService appUserDetailsService, final JwtTokenConfig jwtTokenConfig) {
		this.appUserDetailsService = appUserDetailsService;
		this.jwtTokenConfig = jwtTokenConfig;
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		
		final var authorizationHeader = request.getHeader("Authorization");

		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			final var token = authorizationHeader.substring(7);
			final var authData = jwtTokenConfig.getSubjectFromToken(token);

			if (authData != null && !authData.isBlank() && SecurityContextHolder.getContext().getAuthentication() == null) {
				final var userDetails = appUserDetailsService.loadUserByUsername(authData);
				final var isTokenValid = jwtTokenConfig.isTokenValid(token, userDetails);
				
				if (isTokenValid) {
					final var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
							null, userDetails.getAuthorities());
					
					final var webAuthenticationDetailsSource = new WebAuthenticationDetailsSource()
							.buildDetails(request);
					
					usernamePasswordAuthenticationToken.setDetails(webAuthenticationDetailsSource);
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				}
			}
		}

		filterChain.doFilter(request, response);
	}
}