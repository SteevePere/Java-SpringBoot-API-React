package com.quest.etna.model.dtos.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quest.etna.model.entities.User;
import com.quest.etna.model.enums.UserRole;


public class UserDetails
{
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private UserRole role;
	
	
	public UserDetails(User user)
	{
		this.username = user.getUsername();
		this.role =  user.getRole();
	}

	
	@JsonIgnore
	public String getUsername()
	{
		return this.username;
	}


	public void setUsername(String username)
	{
		this.username = username;
	}


	public String getRole()
	{
		return this.role.name();
	}


	public void setRole(UserRole role)
	{
		this.role = role;
	}
	
	
	@Override
	public String toString()
	{
		String toString = String.format("\nusername=%s, role=%s", username, role);
				
		return toString;
	}
	
	
	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += (username != null ? username.hashCode() : 0);
	
		return hash;
	}
	
	
	@Override
	public boolean equals(Object object)
	{	
		if(!(object instanceof UserDetails))
		{
			return false;
		}
		
		UserDetails other = (UserDetails) object;
		
		if((this.username == null && other.username != null) ||
				(this.username != null && !this.username.equals(other.username)))
		{
			return false;
		}
		
		return true;
	}
}