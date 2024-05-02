package com.vikas.hotelmanagment.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.List;

//import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.vikas.hotelmanagment.security.user.HotelUserDetails;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@ComponentScan("com.vikas.hotelmanagment.secuirty.jwt")
@Component
public class JwtUtils {
private static final Logger  logger = LoggerFactory.getLogger(JwtUtils.class);

@Value("${security.jwt.secret}")
private   final String jwtsecret = "hA8sP2wBn4rYs6qUfZjXn+RcTfWj2n5AcCfFbVr3Yd7gHjMn6TqW3eZrE";



@Value("${security.jwt.expirationTime}")
private final int jwtexpiration = 3600000; 
public String generateJwtTokenForUser(Authentication authentication){
HotelUserDetails userPrincipal = (HotelUserDetails) authentication.getPrincipal();
List<String> roles = userPrincipal.getAuthorities () .stream() .map(GrantedAuthority::getAuthority).toList();
return Jwts.builder()
.setSubject(userPrincipal.getUsername())
.claim ( "roles", roles)
.setIssuedAt(new Date())
.setExpiration(new Date(System.currentTimeMillis() + jwtexpiration))
.signWith(key(), SignatureAlgorithm.HS256).compact();
}
private Key key() {
	// TODO Auto-generated method stub
	System.out.println("the value of jwt secret is"+jwtsecret);
	return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtsecret));
}
public String getUserNameFromToken(String token) {
	System.out.println("came here for get");
return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();	
}
public boolean validatetoken(String token) {
	try {
		System.out.println("came here for validation");
System.out.println(token);
	Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
	System.out.println("came here for validation2");
	return true;
	}catch(MalformedJwtException e) {
	 logger.error("Inavlid jwt token : {}"+e.getMessage());	
	}catch(ExpiredJwtException e) {
		logger.error("Expired Token"+e.getMessage());
	}catch(UnsupportedJwtException e) {
		logger.error("this token is not supported"+e.getMessage());
	}catch(IllegalArgumentException e) {
	logger.error("No claims found : {}",e.getMessage());	
	}
return false;
}
}
