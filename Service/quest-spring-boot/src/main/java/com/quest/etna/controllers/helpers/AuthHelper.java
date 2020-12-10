package com.quest.etna.controllers.helpers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.quest.etna.exceptions.InternalServerErrorException;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.entities.Board;
import com.quest.etna.model.entities.Issue;
import com.quest.etna.model.entities.Milestone;
import com.quest.etna.repositories.BoardRepository;


@Component
public class AuthHelper
{
	
	@Autowired
	private BoardRepository _boardRepository;
	
	
	public JwtUserDetails getCurrentUser() throws Exception
    {
    	JwtUserDetails userDetails;
    	
    	try
    	{
    		Object principal = SecurityContextHolder
    			.getContext()
				.getAuthentication()
				.getPrincipal();

        	userDetails = (JwtUserDetails) principal;
    	}
    	
    	catch (Exception e)
    	{
    		throw new InternalServerErrorException();
    	}
    	
    	return userDetails;
	}
	
	
	public Boolean currentUserCanAccessBoard(Board board) throws Exception
	{
		Boolean canAccessBoard = false;
		
		JwtUserDetails currentUser = this.getCurrentUser();
		List<Board> authorizedBoards = _boardRepository.findByUserId(currentUser.getId());
		
		if (currentUser.isAdmin() || authorizedBoards.contains(board))
			canAccessBoard = true;
		
		return canAccessBoard;
	}
	
	
	public Boolean currentUserCanAccessIssue(Issue issue) throws Exception
	{
		return this.currentUserCanAccessBoard(issue.getBoard());
	}
	
	
	public Boolean currentUserCanAccessMilestone(Milestone milestone) throws Exception
	{
		return this.currentUserCanAccessBoard(milestone.getBoard());
	}
}
