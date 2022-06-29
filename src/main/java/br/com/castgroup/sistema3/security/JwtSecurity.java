package br.com.castgroup.sistema3.security;

import java.io.IOException;
import java.util.List;
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
import io.jsonwebtoken.Jwts;

public class JwtSecurity extends OncePerRequestFilter{
	
	private final String HEADER = "Authorization";
	
	private final String PREFIX = "Bearer";
	
	public static final String SECRET = "5eebb082-4046-4d7f-a638-3c16d9dec4f8";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		try {
			if(CheckJWTToken(request,response)) {
				Claims claims = validateToken(request);
				if(claims.get("authorities") != null) {
					setUpSpringAutentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			}else {
				SecurityContextHolder.clearContext();
			}
			
			
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
			return;
		}
		chain.doFilter(request, response);
	} 
	
	
	private void setUpSpringAutentication(Claims claims) {
		@SuppressWarnings({"unchecked", "rawtypes"})
		List<String> authorities = (List) claims.get("authorities");
		
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
				authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		
	}

	private Claims validateToken(HttpServletRequest request) {
		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		
		return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
	}

	private boolean CheckJWTToken(HttpServletRequest request, HttpServletResponse res) {
		String autenticationHeader = request.getHeader(HEADER);
		if(autenticationHeader == null || !autenticationHeader.startsWith(PREFIX))
			
		return false;
		return true;
	}

}

