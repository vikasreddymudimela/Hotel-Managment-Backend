package com.vikas.hotelmanagment.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String name;
@ManyToMany(mappedBy="roles")
private Collection<User> users = new HashSet<>();
public void assignroletouser(User user) {
	user.getRoles().add(this);
	this.getUsers().add(user);
}
public void removeuserfromrole(User user) {
	user.getRoles().remove(this);
	this.getUsers().remove(user);
}
public void removeallusersfromrole() {
	if(this.getUsers() !=null) {
		List<User> roleusers = this.getUsers().stream().toList();
		roleusers.forEach(this::removeuserfromrole);
	}
}
public String getName() {
	return name!=null ? name:"";
}
public Role(String roleName) {
	// TODO Auto-generated constructor stub
	this.name = roleName;
}
}
