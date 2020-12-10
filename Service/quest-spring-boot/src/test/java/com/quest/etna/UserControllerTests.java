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
import com.quest.etna.model.dtos.users.UpdateUserDTO;
import com.quest.etna.model.entities.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("data/initTests.sql")
@Transactional
public class UserControllerTests
{
	
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil _jwtTokenUtil;

    private ObjectMapper _objectMapper  = new ObjectMapper();

    private String requestMapping = "/user";
    private String updatedTestUserUsername = "Jhonny";

    
    @Test
    public void testFindUsers() throws Exception
    {

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.get(requestMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getAdminBearerToken())
            )
            .andExpect(status().isOk());
    }
    
    
    @Test
    public void testFindOneUser() throws Exception
    {
        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.get(requestMapping + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isForbidden());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.get(requestMapping + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getAdminBearerToken())
            )
            .andExpect(status().isOk());
    }


    @Test
    public void testUpdateUser() throws Exception
    {

        UpdateUserDTO updateUser = new UpdateUserDTO();
        updateUser.setUsername(updatedTestUserUsername);

        String jsonUpdatedUser = _objectMapper.writeValueAsString(updateUser);

       this.mockMvc
            .perform(
                    MockMvcRequestBuilders.patch(requestMapping + "/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonUpdatedUser)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isForbidden());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.patch(requestMapping + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonUpdatedUser)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is(updatedTestUserUsername)));

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.patch(requestMapping + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonUpdatedUser)
                            .header("Authorization", "Bearer " + getAdminBearerToken())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.username", is(updatedTestUserUsername)));


    }

    
    @Test
    public void testDeleteUser() throws Exception {
        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.delete(requestMapping + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isForbidden());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.delete(requestMapping + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getAdminBearerToken())
            )
            .andExpect(status().isOk());
    }

    
    private String getAdminBearerToken()
    {
        String adminUsername = "usernameAdmin";
        String adminPassword = "passwordAdmin";

        User adminUser = new User(adminUsername, adminPassword);
        JwtUserDetails userDetails = new JwtUserDetails(adminUser);

        return _jwtTokenUtil.generateToken(userDetails);
    }


    private String getUserBearerToken()
    {
        String userUsername = "usernameUser";
        String userPassword = "passwordUser";

        User adminUser = new User(userUsername, userPassword);
        JwtUserDetails userDetails = new JwtUserDetails(adminUser);

        return _jwtTokenUtil.generateToken(userDetails);
    }
}