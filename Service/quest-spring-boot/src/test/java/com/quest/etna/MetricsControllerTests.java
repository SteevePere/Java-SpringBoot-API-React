package com.quest.etna;

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

import com.quest.etna.config.security.JwtTokenUtil;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.entities.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("data/initTests.sql")
@Transactional
public class MetricsControllerTests
{

	@Autowired
	protected MockMvc mockMvc;
	
	@Autowired
	private JwtTokenUtil _jwtTokenUtil;
	
	private String _requestMapping = "/metrics";
	
	private String _existingTestAdminUsername = "usernameAdmin";
	private String _existingTestAdminPassword = "passwordAdmin";
	
	private String _existingTestUserUsername = "usernameUser";
	private String _existingTestUserPassword = "passwordUser";
	
	
	@Test
	public void testGetMetrics() throws Exception
	{
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.get(_requestMapping)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getUserBearerToken())
		)
		.andExpect(status().isForbidden());
		
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.get(_requestMapping)
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk());
	}
	
	
	@Test
	public void testGetMetricsExport() throws Exception
	{
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.get(_requestMapping + "/export")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getUserBearerToken())
		)
		.andExpect(status().isForbidden());
		
		this.mockMvc
		.perform(
			MockMvcRequestBuilders.get(_requestMapping + "/export")
			.contentType(MediaType.APPLICATION_JSON)
			.header("Authorization", "Bearer " + getAdminBearerToken())
		)
		.andExpect(status().isOk());
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
