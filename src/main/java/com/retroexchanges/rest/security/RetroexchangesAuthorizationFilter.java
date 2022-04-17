package com.retroexchanges.rest.security;

import java.io.IOException;
import java.util.List;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.JwtException; 

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.GrantedAuthority;

public class RetroexchangesAuthorizationFilter extends OncePerRequestFilter {

	private final String HEADER = "Authorization";
	private final String PREFIX = "Bearer ";
	private final String SECRET = "9mso+Aoz1yLGmnD4XksJBa34CSVYUP0KC3fbZK77IsP5OD0/u54Gta+MvS+cmanq"
			+ "FP7SM9xxSw5+P8wMvYmOIUsmU6YOKbw1a/EqTWSs6kL/v8pf63C4zt6TY+T4UF6o"
			+ "2joWKHCyph9gtC5UQNb9WD2lWr3+nnOI21P9zp6GkEKmObmZmPA3YmTFjhuVc527"
			+ "15FoEA4W2bzS/e0o9ILea7A+HZbMnLTXMOIiTQ==";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {
			if (checkJWTToken(request, response)) {
				Claims claims = validateToken(request);
				if (claims.get("authorities") != null) {
					setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			} else {
				SecurityContextHolder.clearContext();
			}
			chain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
			return;
		}
	}	

	private Claims validateToken(HttpServletRequest request) {
		try {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		return Jwts.parserBuilder().setSigningKey(SECRET.getBytes()).build().parseClaimsJws(jwtToken).getBody();
		}catch(JwtException  ex) {
			
		}
		return null;
	}

	/**
	 * Authentication method in Spring flow
	 * 
	 * @param claims
	 */
	private void setUpSpringAuthentication(Claims claims) {
		@SuppressWarnings("unchecked")
		List<String> authorities = (List<String>) claims.get("authorities");

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);

	}

	private boolean checkJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String authenticationHeader = request.getHeader(HEADER);
		if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX))
			return false;
		return true;
	}
	
	public String getJWTToken(String username, boolean isAdmin) {
				
		List<GrantedAuthority> grantedAuthorities= AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		if (isAdmin == true) {
			grantedAuthorities= AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN");
		}
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				// El token dura 24 horas
				.setExpiration(new Date(System.currentTimeMillis() + (3600000 * 24))) 
				.signWith(SignatureAlgorithm.HS512,SECRET.getBytes()).compact();
		return "Bearer " + token;
	}


}