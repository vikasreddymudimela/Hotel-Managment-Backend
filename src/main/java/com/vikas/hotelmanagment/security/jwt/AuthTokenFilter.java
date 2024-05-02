package com.vikas.hotelmanagment.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vikas.hotelmanagment.repository.UserRepository;
import com.vikas.hotelmanagment.security.user.HotelUserDetails;
import com.vikas.hotelmanagment.security.user.HotelUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
  private   JwtUtils jwtutils;
	@Autowired
  private   HotelUserDetailsService hoteluserdetailsservice;
	
	
	
  private final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
		String jwt = parsejwt(request);
		if(jwtutils==null) {
			System.out.println("jwtutils not found");
		}
		if(jwt!=null && jwtutils.validatetoken(jwt)) {
			System.out.println("came  here for validation of token");
			String email = jwtutils.getUserNameFromToken(jwt);
			UserDetails userdeatils = hoteluserdetailsservice.loadUserByUsername(email);
			System.out.println(userdeatils);
			var authentication = new UsernamePasswordAuthenticationToken( userdeatils,null,userdeatils.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		}
		catch(Exception e) {
			logger.error("cannot set user authentication"+e.getMessage());
		}
		filterChain.doFilter(request, response);
	}
	private String parsejwt(HttpServletRequest request) {
		// TODO Auto-generated method stub
		String headerAuth = request.getHeader("Authorization");
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")) {
		return headerAuth.substring(7);
	}
	return null;

}
	
}