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
import com.quest.etna.model.dtos.issues.CreateIssueDTO;
import com.quest.etna.model.dtos.issues.UpdateIssueDTO;
import com.quest.etna.model.dtos.issues.UpdateIssueStatusDTO;
import com.quest.etna.model.entities.User;
import com.quest.etna.model.enums.IssueStatus;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("data/initTests.sql")
@Transactional
public class IssueControllerTests
{

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	private JwtTokenUtil _jwtTokenUtil;
	
	private ObjectMapper _objectMapper  = new ObjectMapper();
	
	private String _requestMapping = "/issue";
	
	private String _existingTestAdminUsername = "usernameAdmin";
	private String _existingTestAdminPassword = "passwordAdmin";
	
	private String _existingTestUserUsername = "usernameUser";
	private String _existingTestUserPassword = "passwordUser";
	
	private String _existingTestIssueTitle = "Existing Test Issue";
	private String _newTestIssueTitle = "New Test Issue";
	private String _updatedTestIssueTitle = "Updated Test Issue";
	private IssueStatus _updatedTestIssueStatus = IssueStatus.CLOSED;
	
	
	@Test
	public void testGetIssues() throws Exception
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
	public void testGetIssue() throws Exception
	{
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.get(_requestMapping + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(1)))
		.andExpect(jsonPath("$.title", is(_existingTestIssueTitle)));
	}
	
	
	@Test
	public void testCreateIssue() throws Exception
	{
		CreateIssueDTO newIssue = new CreateIssueDTO();
		
		newIssue.setTitle(_newTestIssueTitle);
		newIssue.setBoardId(1);
		newIssue.setReporterId(1);
		
        String jsonNewIssue = _objectMapper.writeValueAsString(newIssue);
		
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.post(_requestMapping)
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonNewIssue)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.title", is(_newTestIssueTitle)));
	}
	
	
	@Test
	public void testUpdateIssue() throws Exception
	{
		UpdateIssueDTO updatedIssue = new UpdateIssueDTO();
		updatedIssue.setTitle(_updatedTestIssueTitle);
		
        String jsonUpdatedIssue = _objectMapper.writeValueAsString(updatedIssue);
        
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.patch(_requestMapping + "/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonUpdatedIssue)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.title", is(_updatedTestIssueTitle)));
	}
	
	
	@Test
	public void testUpdateIssueStatus() throws Exception
	{
		UpdateIssueStatusDTO updatedIssue = new UpdateIssueStatusDTO();
		updatedIssue.setStatus(_updatedTestIssueStatus);
		
        String jsonUpdatedIssue = _objectMapper.writeValueAsString(updatedIssue);
        
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.patch(_requestMapping + "/status/1")
			.contentType(MediaType.APPLICATION_JSON)
			.content(jsonUpdatedIssue)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status", is(_updatedTestIssueStatus.toString())));
	}
	
	
	@Test
	public void testDeleteIssue() throws Exception
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
