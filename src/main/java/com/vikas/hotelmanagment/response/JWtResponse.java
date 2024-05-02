package com.vikas.hotelmanagment.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JWtResponse {
public JWtResponse(Long id2, String email2, String jwt, List<String> roles2) {
		
	this.id=id2;
	this.email=email2;
	this.token = jwt;
	this.roles = roles2;
	// TODO Auto-generated constructor stub
	}
private Long id;
private String email;
private String token;
private String type ="Bearer";
private List<String> roles;

}
