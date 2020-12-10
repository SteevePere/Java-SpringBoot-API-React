package com.quest.etna.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;


@Component
public class JwtRequestFilter extends OncePerRequestFilter

{
	@Autowired
	private JwtUserDetailsService _jwtUserDetailsService;
	
	@Autowired
	private JwtTokenUtil _jwtTokenUtil;
	
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain
	) throws ServletException, IOException
	{
		final String requestTokenHeader = request.getHeader("Authorization");
	
		String username = null;
		String token = null;
		
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer "))
		{
			token = requestTokenHeader.substring(7);
		
			try
			{
				username = _jwtTokenUtil.getUsernameFromToken(token);
			}
			
			catch (IllegalArgumentException e)
			{
				System.out.println("Unable to get JWT Token");
			}
			
			catch (ExpiredJwtException e)
			{
				System.out.println("JWT Token has expired");
			}
		}
		
		if (username != null &&
			SecurityContextHolder.getContext()
			.getAuthentication() == null)
		{
			UserDetails userDetails = this._jwtUserDetailsService.loadUserByUsername(username);

			if (_jwtTokenUtil.validateToken(token, userDetails))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
					new UsernamePasswordAuthenticationToken(
						userDetails,
						null,
						userDetails.getAuthorities()
					);
				
				usernamePasswordAuthenticationToken
					.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext()
					.setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		
		chain.doFilter(request, response);
	}
}