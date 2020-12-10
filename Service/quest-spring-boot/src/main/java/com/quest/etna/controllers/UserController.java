package com.quest.etna.controllers;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.etna.controllers.helpers.AuthHelper;
import com.quest.etna.controllers.helpers.EmailHelper;
import com.quest.etna.exceptions.BadRequestException;
import com.quest.etna.exceptions.ConflictException;
import com.quest.etna.exceptions.ForbiddenException;
import com.quest.etna.exceptions.NotFoundException;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.dtos.general.ReadSuccessDTO;
import com.quest.etna.model.dtos.users.ToggleUserDTO;
import com.quest.etna.model.dtos.users.UpdateUserDTO;
import com.quest.etna.model.entities.User;
import com.quest.etna.repositories.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;


@RequestMapping("/user")
@RestController
@Tag(name = "Users", description = "User-related Endpoints.")
public class UserController
{
	
	@Autowired
	private UserRepository _userRepository;
	
	@Autowired
	private AuthHelper _authHelper;
	
	@Autowired
	private EmailHelper _emailHelper;
	
	@Autowired
	private PasswordEncoder _passwordEncoder;
	
	
	@GetMapping()
	public List<User> getUsers() throws Exception
	{
		return (List<User>) _userRepository.findAll();
	}
	
	
	@GetMapping("/export")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<InputStreamResource> getUsersDownload(HttpServletResponse response)
		throws JsonProcessingException
	{
		List<User> users = (List<User>) _userRepository.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		
		String fileName = "users.json";
		
		response.addHeader("Content-disposition", "attachment;filename=" + fileName);
		response.setContentType("application/octet-stream");
		
		byte[] buf = mapper.writeValueAsBytes(users);

		return ResponseEntity
			.ok()
			.contentLength(buf.length)
			.contentType(
				MediaType.parseMediaType("application/octet-stream"))
				.body(new InputStreamResource(new ByteArrayInputStream(buf)));
	}
	
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable int id) throws Exception
	{
		JwtUserDetails currentUser = _authHelper.getCurrentUser();		
		Optional<User> userToGet = _userRepository.findById(id);
		
		if (!userToGet.isPresent())
			throw new NotFoundException("User not found.");
		
		else if (!currentUser.isAdmin() &&
			userToGet.get().getId() != currentUser.getId())
		{
			throw new ForbiddenException("You are not authorized to access this User.");
		}

		return userToGet.get();
	}
	
	
	@PatchMapping("/{id}")
	public User modifyUser(
		@PathVariable int id,
		@RequestBody UpdateUserDTO fieldsToUpdate
	) throws Exception
	{
		JwtUserDetails currentUser = _authHelper.getCurrentUser();
		Optional<User> user = _userRepository.findById(id);
		
		if (!user.isPresent())
			throw new NotFoundException("User not found.");
		
		User userToUpdate = user.get();
		
		if (!currentUser.isAdmin() && userToUpdate.getId() != currentUser.getId())
			throw new ForbiddenException("You are not authorized to modify this User.");
		
		if (fieldsToUpdate.getUsername() != null)
		{
			User existingUser = _userRepository.findByUsername(fieldsToUpdate.getUsername());
			
			if (existingUser != null && existingUser.getId() != id)
	    		throw new ConflictException("This Username is already in use."); // name is taken by a different user entity
			
			userToUpdate.setUsername(fieldsToUpdate.getUsername());
		}
		
		if (fieldsToUpdate.getRole() != null && currentUser.isAdmin())
			userToUpdate.setRole(fieldsToUpdate.getRole());
		
		if (fieldsToUpdate.getPassword() != null)
			userToUpdate.setPassword(_passwordEncoder.encode(fieldsToUpdate.getPassword()));
		
		User savedUser = _userRepository.save(userToUpdate);		

		return savedUser;
	}
	
	
	@PatchMapping("/activate/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<User> toggleUser(
		@PathVariable int id,
		@RequestBody ToggleUserDTO fieldsToUpdate
	) throws Exception
	{
		if (fieldsToUpdate.getIsActive() == null)
			throw new BadRequestException("isActive is required.");
		
		Optional<User> user = _userRepository.findById(id);
		
		if (!user.isPresent())
			throw new NotFoundException("User not found.");
		
		User userToUpdate = user.get();
		userToUpdate.setIsActive(fieldsToUpdate.getIsActive());
		User savedUser = _userRepository.save(userToUpdate);
		
		if (savedUser.getIsActive())
		{
			_emailHelper.sendEmail(
				"Welcome to your new Account", 
				"Your account has been activated. You may now sign in!"
			);
		}
		
		else
		{
			_emailHelper.sendEmail(
				"Your Account has been blocked", 
				"You will be unable to sign in. Please contact an Administrator."
			);	
		}
		
		return new ResponseEntity<User>(savedUser, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ReadSuccessDTO> deleteUser(@PathVariable int id) throws Exception
	{
		ReadSuccessDTO response = new ReadSuccessDTO(false);
		Optional<User> userToDelete = _userRepository.findById(id);
		
		if (!userToDelete.isPresent())
			throw new NotFoundException("User not found.");
		
		try
		{
			userToDelete.get().getBoards().clear();
			_userRepository.delete(userToDelete.get());
			response.setSuccess(true);
		}
		
		catch (Exception e)
		{
			response.setSuccess(false);
		}
		
		return new ResponseEntity<ReadSuccessDTO>(response, HttpStatus.OK);
	}
}
