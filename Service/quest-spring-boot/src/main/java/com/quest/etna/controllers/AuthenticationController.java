package com.quest.etna.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.config.security.JwtTokenUtil;
import com.quest.etna.config.security.JwtUserDetailsService;
import com.quest.etna.exceptions.BadRequestException;
import com.quest.etna.exceptions.ConflictException;
import com.quest.etna.exceptions.InternalServerErrorException;
import com.quest.etna.model.dtos.auth.AuthenticateDTO;
import com.quest.etna.model.dtos.auth.JwtResponse;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.dtos.auth.RegisterDTO;
import com.quest.etna.model.entities.User;
import com.quest.etna.repositories.UserRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;


@RequestMapping("")
@RestController
@Tag(name = "Auth", description = "Authorization-related Endpoints.")
public class AuthenticationController
{
	
	@Autowired
	private UserRepository _userRepository;

	@Autowired
	private AuthenticationManager _authenticationManager;

	@Autowired
	private JwtTokenUtil _jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService _jwtUserDetailsService;
	
	@Autowired
	private PasswordEncoder _passwordEncoder;
	
	
    @PostMapping("/register")
    @SecurityRequirements
    public ResponseEntity<User> register(@RequestBody RegisterDTO userToCreate)
    {	
    	User userToPersist;
    	
    	if (userToCreate.getUsername() == null || userToCreate.getPassword() == null)
    		throw new BadRequestException("Please make sure all values are correct.");
    	
    	if (_userRepository.findByUsername(userToCreate.getUsername()) != null)
    		throw new ConflictException("This Username is already in use.");

    	try	
    	{
    		userToCreate.setPassword(_passwordEncoder.encode(userToCreate.getPassword()));
    		
    		userToPersist = new User(
				userToCreate.getUsername(),
				userToCreate.getPassword(),
				userToCreate.getRole()
			);
    		
    		_userRepository.save(userToPersist);
    	}

    	catch (Exception e)
    	{
    		
    		throw new InternalServerErrorException();
    	}

    	return new ResponseEntity<User>(userToPersist, HttpStatus.CREATED);
    }
    
    
    @PostMapping("/authenticate")
    @SecurityRequirements
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticateDTO user) throws Exception
    {
    	if (user.getUsername() == null || 
			user.getPassword() == null)
    	{
    		throw new BadRequestException("Please make sure all values are correct.");
    	}
    	
		authenticate(user.getUsername(), user.getPassword());
		
		final JwtUserDetails userDetails = _jwtUserDetailsService
			.loadUserByUsername(user.getUsername());
		
		final String token = _jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new JwtResponse(token));
	}
    
    
    private void authenticate(String username, String password) throws Exception
    {
		try
		{
			_authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password)
			);
		}
		
		catch (Exception e)
		{
			throw new InternalServerErrorException("Authentication error.");
		}
	}
}
