package com.bezkoder.springjwt.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(	name = "gusers", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "name") 
		})
public class GUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	@Email
	private String username;

	@NotBlank
	@Size(max = 150)
	private String name;

//	@NotBlank
//	@Size(max = 120)
//	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "guser_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JsonManagedReference
	@JoinColumn(name = "prof_id")
	GProfile profile;

	
	public GUser() {
	}

	public GUser(String username, String name) {
		this.username = username;
		this.name = name;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public GProfile getProfile() {
		return profile;
	}

	public void setProfile(GProfile profile) {
		this.profile = profile;
	}
	
	
	//helper method
	public void addProfile(GProfile p)
	{
		this.profile = p;
		p.setUser(this);
	}
	public void removeProfile(GProfile p)
	{
		this.profile = null;
		p.setUser(null);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", name=" + name  + ", roles="
				+ roles + "]";
	}

	
}
