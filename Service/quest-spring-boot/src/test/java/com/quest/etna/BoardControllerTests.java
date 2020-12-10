package com.quest.etna;

import static org.hamcrest.CoreMatchers.is;

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
import com.quest.etna.config.security.JwtTokenUtil;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.dtos.boards.CreateBoardDTO;
import com.quest.etna.model.dtos.boards.UpdateBoardDTO;
import com.quest.etna.model.entities.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("data/initTests.sql")
@Transactional
public class BoardControllerTests
{

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	private JwtTokenUtil _jwtTokenUtil;
	
	private ObjectMapper _objectMapper  = new ObjectMapper();
	
	private String _requestMapping = "/board";
	
	private String _existingTestAdminUsername = "usernameAdmin";
	private String _existingTestAdminPassword = "passwordAdmin";
	
	private String _existingTestUserUsername = "usernameUser";
	private String _existingTestUserPassword = "passwordUser";
	
	private String _existingTestBoardTitle = "Existing Test Board";
	private String _newTestBoardTitle = "New Test Board";
	private String _updatedTestBoardTitle = "Updated Test Board";
	
	
	@Test
	public void testGetBoards() throws Exception
	{
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.get(_requestMapping)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.length()", is(1)));
	}
	
	
	@Test
	public void testGetBoard() throws Exception
	{
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.get(_requestMapping + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.title", is(_existingTestBoardTitle)));
	}
	
	
	@Test
	public void testCreateBoard() throws Exception
	{
		CreateBoardDTO newBoard = new CreateBoardDTO();
		newBoard.setTitle(_newTestBoardTitle);
		
        String jsonNewBoard = _objectMapper.writeValueAsString(newBoard);
		
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.post(_requestMapping)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonNewBoard)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isCreated());
		
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.post(_requestMapping)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonNewBoard)
			.header("Authorization", "Bearer " + getUserBearerToken())
		)
		.andExpect(status().isForbidden());
	}
	
	
	@Test
	public void testUpdateBoard() throws Exception
	{
		UpdateBoardDTO updatedBoard = new UpdateBoardDTO();
		updatedBoard.setTitle(_updatedTestBoardTitle);
		
        String jsonUpdatedBoard = _objectMapper.writeValueAsString(updatedBoard);
        
        this.mockMvc
		.perform(
			MockMvcRequestBuilders.patch(_requestMapping + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonUpdatedBoard)
			.header("Authorization", "Bearer " + getUserBearerToken())
		)
		.andExpect(status().isForbidden());
        
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.patch(_requestMapping + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonUpdatedBoard)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title", is(_updatedTestBoardTitle)));
	}
	
	
	@Test
	public void testDeleteBoard() throws Exception
	{
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.delete(_requestMapping + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getUserBearerToken())
		)
		.andExpect(status().isForbidden());
		
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.delete(_requestMapping + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.success", is(true)));
	}
	
	
	private String getAdminBearerToken()
	{
		User adminUser = new User(_existingTestAdminUsername, _existingTestAdminPassword);
		JwtUserDetails userDetails = new JwtUserDetails(adminUser);
		
		return _jwtTokenUtil.generateToken(userDetails);
	}
	
	
	private String getUserBearerToken()
	{
		User userUser = new User(_existingTestUserUsername, _existingTestUserPassword);
		JwtUserDetails userDetails = new JwtUserDetails(userUser);
		
		return _jwtTokenUtil.generateToken(userDetails);
	}
}
