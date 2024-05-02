package com.vikas.hotelmanagment.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.hotelmanagment.Exception.UserAlreadyExistsException;
import com.vikas.hotelmanagment.Exception.UsernameNotFoundException;
import com.vikas.hotelmanagment.model.User;
import com.vikas.hotelmanagment.request.LoginRequest;
import com.vikas.hotelmanagment.response.JWtResponse;
import com.vikas.hotelmanagment.security.jwt.JwtUtils;
import com.vikas.hotelmanagment.security.user.HotelUserDetails;
import com.vikas.hotelmanagment.service.Userservice;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {
	
	private final Userservice userservice;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtutils;
	@PostMapping("/register-user")
public ResponseEntity<?> registeruser(@RequestBody User user){
	try {
		System.out.println("register came here");
		userservice.registeruser(user);
		
		return ResponseEntity.ok("Registartion successful");
	}catch(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}catch(UserAlreadyExistsException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
	}
}
	@PostMapping("/login")
	public ResponseEntity<?> authenticateuser(@Valid @RequestBody LoginRequest loginrequest){
Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginrequest.getEmail(),loginrequest.getPassword()));
SecurityContextHolder.getContext().setAuthentication(authentication);
String jwt = jwtutils.generateJwtTokenForUser(authentication);
HotelUserDetails userDetails = (HotelUserDetails )authentication.getPrincipal();
List<String> roles =  userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
return ResponseEntity.ok(new JWtResponse(userDetails.getId(),userDetails.getEmail(),jwt,roles) );
	}
}
