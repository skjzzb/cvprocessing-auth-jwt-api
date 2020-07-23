package com.bezkoder.springjwt.payload.request;

import java.util.Set;

import javax.validation.constraints.*;
 
public class GSignupRequest {
    @NotBlank
    @Size(max = 120)
    @Email
    private String username;
 
    @NotBlank
    @Size(max = 150)
    
    private String name;
    
    private Set<String> role;
    

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


    
    public Set<String> getRole() {
      return this.role;
    }
    
    public void setRole(Set<String> role) {
      this.role = role;
    }
}
