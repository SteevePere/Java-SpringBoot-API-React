package com.quest.etna;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quest.etna.model.dtos.auth.AuthenticateDTO;
import com.quest.etna.model.dtos.auth.RegisterDTO;



@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("data/initTests.sql")
@Transactional
public class AuthControllerTests
{

	@Autowired
	protected MockMvc mockMvc;
	
	private ObjectMapper _objectMapper  = new ObjectMapper();
	
	private String _registerRequestMapping = "/register";
	private String _authenticateRequestMapping = "/authenticate";
	
	private String _existingTestUserUsername = "usernameUser";
	private String _existingTestUserPassword = "passwordUser";
	
	private String _newTestUserUsername = "newUsernameUser";
	private String _newTestUserPassword = "newPasswordUser";
	
	
	@Test
	public void testRegister() throws Exception
	{
		RegisterDTO userToCreate = new RegisterDTO();
		
		userToCreate.setUsername(_newTestUserUsername);
		userToCreate.setPassword(_newTestUserPassword);
		
		String jsonNewUser = _objectMapper.writeValueAsString(userToCreate);
		
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post(_registerRequestMapping)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonNewUser)
			)
			.andExpect(status().isCreated());
	}
	
	
	@Test
	public void testRegisterConflict() throws Exception
	{
		RegisterDTO userToCreate = new RegisterDTO();
		
		userToCreate.setUsername(_existingTestUserUsername);
		userToCreate.setPassword(_existingTestUserPassword);
		
		String jsonNewUser = _objectMapper.writeValueAsString(userToCreate);
		
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post(_registerRequestMapping)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonNewUser)
			)
			.andExpect(status().isConflict());
	}
	
	
	@Test
	public void testAuthenticate() throws Exception
	{
		AuthenticateDTO userToCreate = new AuthenticateDTO();
		
		userToCreate.setUsername(_existingTestUserUsername);
		userToCreate.setPassword(_existingTestUserPassword);
		
		String jsonUser = _objectMapper.writeValueAsString(userToCreate);
		
		this.mockMvc
			.perform(
				MockMvcRequestBuilders.post(_authenticateRequestMapping)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonUser)
			)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.token").isString());
	}
}
