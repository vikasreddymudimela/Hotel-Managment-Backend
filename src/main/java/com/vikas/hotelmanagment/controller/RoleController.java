package com.vikas.hotelmanagment.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vikas.hotelmanagment.Exception.RoleAlreadyExistException;
import com.vikas.hotelmanagment.model.Role;
import com.vikas.hotelmanagment.model.User;
import com.vikas.hotelmanagment.request.AssignRoleRequest;
import com.vikas.hotelmanagment.response.RoleResponse;
import com.vikas.hotelmanagment.response.RoleforRoleresponse;
import com.vikas.hotelmanagment.response.UserforRoleResponse;
import com.vikas.hotelmanagment.service.RoleService;
import com.vikas.hotelmanagment.service.Userservice;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")

public class RoleController {

	private final RoleService roleservice;
	
	@GetMapping("/allroles")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	
	public ResponseEntity<List<RoleforRoleresponse>> getallroles(){
		System.out.println("came here2now");
		List<Role> roles = roleservice.getRoles();
		List<RoleforRoleresponse> roleresponse = new ArrayList<>();
		for(int i=0;i<roles.size();i++) {
			Collection<User> users = roles.get(i).getUsers();
			Iterator<User> i1 = users.iterator();
			List<UserforRoleResponse> lus = new ArrayList<>();
			
			while(i1.hasNext()) {
				User user = i1.next();
				UserforRoleResponse userforrole = new UserforRoleResponse(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail());
		        lus.add(userforrole);      		
			}
			RoleforRoleresponse rolefor = new RoleforRoleresponse(roles.get(i).getId(),roles.get(i).getName(),lus);
		     roleresponse.add(rolefor);
		}
		return new ResponseEntity<> (roleresponse, HttpStatus.OK);
		
	}
	
	@GetMapping("/role/{roleid}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	
	public ResponseEntity<List<UserforRoleResponse>> getallroles(@PathVariable("roleid") Long roleid){
		Role role = roleservice.getRoleById(roleid);
		Collection<User> users = role.getUsers();
		Iterator<User> i1 = users.iterator();
		List<UserforRoleResponse> lus = new ArrayList<>();
		
		while(i1.hasNext()) {
			User user = i1.next();
			UserforRoleResponse userforrole = new UserforRoleResponse(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail());
	        lus.add(userforrole);      		
		}
		RoleforRoleresponse rolefor = new RoleforRoleresponse(role.getId(),role.getName(),lus);
	
		
		return new ResponseEntity<> (lus, HttpStatus.OK);
		
	}
	
	@PostMapping("/createrole")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	
	public ResponseEntity<String> createrole(@RequestBody Role theRole){
		try{
			System.out.println("came here");
			roleservice.createRole (theRole);
			return ResponseEntity.ok ( "New role created successfully!");
			}catch(RoleAlreadyExistException re){
			return ResponseEntity.status(HttpStatus.CONFLICT).body (re.getMessage());
			}
	}
	
	@DeleteMapping("/delete/{roleid}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
 public ResponseEntity<String> deleteRole(@PathVariable("roleid") Long roleid) {
		System.out.println("roleid"+roleid);
	  roleservice.deleteRole(roleid);
	  return ResponseEntity.ok("role deleted successfully");
 }
	
	@PostMapping("/remove-all-users-from-role/{roleid}")
	@PreAuthorize("hasRole('ROLE_ADMIN') ")
	public Role removeallusersfromrole(@PathVariable("roleid") Long roleid) {
		return roleservice.removeAllUsersFromRole(roleid);
	}
	@PostMapping("/remove-user-from-role")
	@PreAuthorize("hasRole('ROLE_ADMIN') ")
	public UserforRoleResponse removeUserFromRole(
			@RequestBody AssignRoleRequest assignrolreq) {
		System.out.println("assignuserid"+assignrolreq.getUserid());
		System.out.println("assignroledid"+assignrolreq.getRoleid());
			User user= roleservice.removeUserFromRole (assignrolreq.getUserid(), assignrolreq.getRoleid());
	     UserforRoleResponse userfor = new UserforRoleResponse(user.getId(), user.getFirstname(), user.getLastname(), user.getEmail());	
	return userfor;
	}
	@PostMapping("/assign-user-to-role")
	@PreAuthorize("hasRole('ROLE_ADMIN') ")
	public User assignUserToRole(
			@RequestBody AssignRoleRequest assignrolreq) {
		System.out.println("assignuserid"+assignrolreq.getUserid());
		System.out.println("assignroledid"+assignrolreq.getRoleid());
			return roleservice.assignRoleToUser(assignrolreq.getUserid(),assignrolreq.getRoleid());
			}
			

}
