package com.vikas.hotelmanagment.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.vikas.hotelmanagment.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor

public class UserResponse {
	private Long id;
	private String email;
	private  String firstname;
	private String lastname;
	private List<RoleResponse> roles ;
	
	public UserResponse(Long id,String email, String firstname,String lastname) {
   System.out.println("object created");
   this.id=id;
       this.email =  email;
		this.firstname = firstname;
		this.lastname = lastname;
		System.out.print(this+"came here for now");
		
	}
	
	public void setroles(Collection<Role> collectionv) {
		System.out.println("");
		List<RoleResponse> ll = new ArrayList<>();
		Iterator<Role> i = collectionv.iterator();
		while(i.hasNext()) {
			Role role = i.next(); 
			ll.add(new RoleResponse(role.getId(), role.getName()));
		}
	 this.roles = ll;	
	System.out.println(this.getRoles()+"at now needed");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public List<RoleResponse> getRoles() {
		return roles;
	}

	
	
	
}
