package com.quest.etna.model.dtos.auth;

import java.io.Serializable;

import com.quest.etna.model.enums.UserRole;


public class RegisterDTO implements Serializable
{
	
	private static final long serialVersionUID = 5926468583005150707L;
	
	private String username;
	private String password;
	private UserRole role;

	
	public RegisterDTO()
	{
		
	}
	
	
	public RegisterDTO(String username, String password)
	{
		this.setUsername(username);
		this.setPassword(password);
	}
	
	
	public String getUsername()
	{
		return this.username;
	}
	
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	
	public String getPassword()
	{
		return this.password;
	}
	
	
	public void setPassword(String password)
	{
		this.password = password;
	}


	public UserRole getRole()
	{
		return role;
	}


	public void setRole(UserRole role)
	{
		this.role = role;
	}
}
