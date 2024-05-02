package com.vikas.hotelmanagment.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private String firstname;
private String lastname;
private String email;
private String password;
@ManyToMany(fetch = FetchType.EAGER,cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH})
@JoinTable(name="user_roles",joinColumns=@JoinColumn(name="user_id",referencedColumnName = "id"),
inverseJoinColumns=@JoinColumn(name="role_id",referencedColumnName = "id"))
private Collection<Role> roles = new HashSet<>();
}
