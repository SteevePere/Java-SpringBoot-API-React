package com.quest.etna.model.dtos.users;

import com.quest.etna.model.enums.UserRole;


public class UpdateUserDTO
{

	private String username;
	private String password;
	private UserRole role;
	
	
	public String getUsername()
	{
		return username;
	}
	
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	
	public String getPassword()
	{
		return password;
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
