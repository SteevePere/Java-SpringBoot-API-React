package com.quest.etna.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.quest.etna.exceptions.UnauthorizedException;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.entities.User;
import com.quest.etna.repositories.UserRepository;


@Service
public class JwtUserDetailsService implements UserDetailsService
{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public JwtUserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException
	{
		User user = userRepository.findByUsername(username);
		
		if (user == null)
    	{
    		throw new UsernameNotFoundException("User not found.");
    	}
		
		if (!user.getIsActive())
		{
			throw new UnauthorizedException();
		}
		
		JwtUserDetails jwtUserDetails = new JwtUserDetails(user);
		
		return jwtUserDetails;
	}

}
