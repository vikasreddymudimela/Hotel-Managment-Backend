package com.vikas.hotelmanagment.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.hotelmanagment.Exception.UsernameNotFoundException;
import com.vikas.hotelmanagment.model.Role;
import com.vikas.hotelmanagment.model.User;
import com.vikas.hotelmanagment.response.RoleResponse;
import com.vikas.hotelmanagment.response.UserResponse;
import com.vikas.hotelmanagment.service.Userservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
private final Userservice userservice;
@GetMapping("/all")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<List<UserResponse>> getUsers(){
	List<User> users = userservice.getusers();
	List<UserResponse> userresponses = new ArrayList<>();
	for(int i=0;i<users.size();i++) {
		User theuser = userservice.getuser(users.get(i).getEmail());
		UserResponse  userrespo = new UserResponse(theuser.getId(),theuser.getEmail(),theuser.getFirstname(),theuser.getLastname());
		  userrespo.setroles(theuser.getRoles());
		  userresponses.add(userrespo);
	}
	return new  ResponseEntity<>(userresponses,HttpStatus.OK);
}
@GetMapping("/profile/{userid}")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public ResponseEntity<?> getuserbyid(@PathVariable("userid") String email){
	try {
		User theuser = userservice.getuser(email);
		System.out.println(theuser.getId());
//		System.out.println(theuser.getRoles());
		
		if(theuser!=null) {
			UserResponse  userrespo = new UserResponse(theuser.getId(),theuser.getEmail(),theuser.getFirstname(),theuser.getLastname());
		  userrespo.setroles(theuser.getRoles());
		 // System.out.println(userrespo.getRoles()+"now needed for");
			return ResponseEntity.ok(userrespo);
		}
		else {
			throw new UsernameNotFoundException("The user doesnot exist");
		}
		
	}catch(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}catch(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error fetching user");
	}
}

@GetMapping("/{email}")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public ResponseEntity<?> getuserbyemail(@PathVariable("email") String email){
	try {
		User theuser = userservice.getuser(email);
		return ResponseEntity.ok(theuser);
	}catch(UsernameNotFoundException e) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}catch(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error fetching user");
	}
}
@DeleteMapping("/delete/{email}")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN') and #email == principal.username")
public ResponseEntity<String> deleteuser(@PathVariable("email") String email){
try {
	System.out.println("email"+email);
	userservice.deleteuser(email);
	return ResponseEntity.ok("delete the user");
}catch(UsernameNotFoundException e) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
}catch(Exception e) {
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
}
}
@DeleteMapping("/deleteuser/{email}")
@PreAuthorize(" hasRole('ROLE_ADMIN') ")
public ResponseEntity<String> deleteuser2(@PathVariable("email") String email){
try {
	System.out.println("email"+email);
	userservice.deleteuser(email);
	return ResponseEntity.ok("deleted the user");
}catch(UsernameNotFoundException e) {
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
}catch(Exception e) {
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
}
}
}
