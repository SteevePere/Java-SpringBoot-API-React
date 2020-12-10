package com.quest.etna.model.dtos.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.quest.etna.model.entities.User;
import com.quest.etna.model.enums.UserRole;


public class JwtUserDetails implements UserDetails
{

	private static final long serialVersionUID = 1L;

	@JsonProperty
	private String username;
	
	@JsonProperty
	private UserRole role;
	
	private User user;
	
	
	public JwtUserDetails(User user)
	{
		this.user = user;
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
		if(!(object instanceof JwtUserDetails))
		{
			return false;
		}
		
		JwtUserDetails other = (JwtUserDetails) object;
		
		if((this.username == null && other.username != null) ||
				(this.username != null && !this.username.equals(other.username)))
		{
			return false;
		}
		
		return true;
	}


	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority(this.getRole()));

        return authorities;
	}

	
	@JsonIgnore
	public String getPassword()
	{
		return this.user.getPassword();
	}
	
	
	@JsonIgnore
	public Integer getId()
	{
		return this.user.getId();
	}


	@JsonIgnore
	public boolean isAccountNonExpired()
	{	
		return true;
	}


	@JsonIgnore
	public boolean isAccountNonLocked()
	{
		return true;
	}


	@JsonIgnore
	public boolean isCredentialsNonExpired()
	{
		return true;
	}


	public boolean isEnabled()
	{
		return user.getIsActive();
	}
	
	
	public boolean isAdmin()
	{
		return user.getIsAdmin();
	}
}
