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
import com.quest.etna.model.dtos.milestones.UpdateMilestoneDTO;
import com.quest.etna.model.dtos.milestones.WriteMilestoneDTO;
import com.quest.etna.model.entities.User;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql("data/initTests.sql")
@Transactional
public class MilestoneControllerTests
{
	
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private JwtTokenUtil _jwtTokenUtil;

    private ObjectMapper _objectMapper  = new ObjectMapper();

    private String requestMapping = "/milestone";
    private String updatedTestMilestoneTitle = "Update Milestone";


    @Test
    public void testCreateMilestone() throws Exception
    {
        WriteMilestoneDTO newMilestoneNull = new WriteMilestoneDTO();
        newMilestoneNull.setTitle("Test Milestone");

        WriteMilestoneDTO newMilestone = new WriteMilestoneDTO();
        newMilestone.setDescription("This is a simple test Milestone");
        newMilestone.setTitle("Test Milestone");
        newMilestone.setBoardId(1);

        String jsonNewMilestoneNull = _objectMapper.writeValueAsString(newMilestoneNull);
        String jsonNewMilestone = _objectMapper.writeValueAsString(newMilestone);

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.post(requestMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonNewMilestoneNull)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isBadRequest());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.post(requestMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonNewMilestone)
                            .header("Authorization", "Bearer " + getAdminBearerToken())
            )
            .andExpect(status().isCreated());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.post(requestMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonNewMilestone)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isForbidden());
    }
    

    @Test
    public void testGetMilestones() throws Exception
    {
        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.get(requestMapping)
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isOk());
    }


    @Test
    public void testGetOneMilestones() throws Exception
    {
        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.get(requestMapping + "/3")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isNotFound());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.get(requestMapping + "/7")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getAdminBearerToken())
            )
            .andExpect(status().isOk());
    }
    

    @Test
    public void testUpdateMilestones() throws Exception
    {

        UpdateMilestoneDTO updateMilestone = new UpdateMilestoneDTO();
        updateMilestone.setTitle(updatedTestMilestoneTitle);

        String jsonUpdatedMilestone = _objectMapper.writeValueAsString(updateMilestone);

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.patch(requestMapping + "/7")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonUpdatedMilestone)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isForbidden());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.patch(requestMapping + "/7")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonUpdatedMilestone)
                            .header("Authorization", "Bearer " + getAdminBearerToken())
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title", is(updatedTestMilestoneTitle)));
    }
    

    @Test
    public void testDeleteMilestones() throws Exception
    {
        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.delete(requestMapping + "/7")
                            .contentType(MediaType.APPLICATION_JSON)
                            .header("Authorization", "Bearer " + getUserBearerToken())
            )
            .andExpect(status().isForbidden());

        this.mockMvc
            .perform(
                    MockMvcRequestBuilders.delete(requestMapping + "/7")
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