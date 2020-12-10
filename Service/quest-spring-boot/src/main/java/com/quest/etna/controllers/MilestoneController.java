package com.quest.etna.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quest.etna.controllers.helpers.AuthHelper;
import com.quest.etna.exceptions.BadRequestException;
import com.quest.etna.exceptions.ForbiddenException;
import com.quest.etna.exceptions.InternalServerErrorException;
import com.quest.etna.exceptions.NotFoundException;
import com.quest.etna.model.dtos.auth.JwtUserDetails;
import com.quest.etna.model.dtos.general.ReadSuccessDTO;
import com.quest.etna.model.dtos.milestones.WriteMilestoneDTO;
import com.quest.etna.model.entities.Board;
import com.quest.etna.model.entities.Milestone;
import com.quest.etna.model.entities.Issue;
import com.quest.etna.repositories.BoardRepository;
import com.quest.etna.repositories.IssueRepository;
import com.quest.etna.repositories.MilestoneRepository;

import io.swagger.v3.oas.annotations.tags.Tag;


@RequestMapping("/milestone")
@RestController
@Tag(name = "Milestones", description = "Milestone-related Endpoints.")
public class MilestoneController
{
	
	@Autowired
	private MilestoneRepository _milestoneRepository;
	
	@Autowired
	private BoardRepository _boardRepository;
	
	@Autowired
	private IssueRepository _issueRepository;
	
	@Autowired
	private AuthHelper _authHelper;
	
	
	@GetMapping()
	public List<Milestone> getMilestones(@RequestParam("boardId") Optional<Integer> boardId) throws Exception
	{
		List<Milestone> milestones = new ArrayList<>();
		JwtUserDetails currentUser = _authHelper.getCurrentUser();
		
		if (currentUser.isAdmin())
		{
			if (boardId.isPresent())
				milestones = _milestoneRepository.findByBoardId(boardId.get());
			
			else
				milestones = (List<Milestone>) _milestoneRepository.findAll();
		}
		
		else
		{
			if (boardId.isPresent())
				milestones = _milestoneRepository.findByBoardAndUserId(boardId.get(), currentUser.getId());
			
			else
				milestones = _milestoneRepository.findByUserId(currentUser.getId());
		}
		
		return milestones;
	}
	
	
	@GetMapping("/{id}")
	public Milestone getMilestone(@PathVariable int id) throws Exception
	{
		Optional<Milestone> milestone = _milestoneRepository.findById(id);
		
		if (!milestone.isPresent())		
			throw new NotFoundException("Milestone not found.");
		
		if (!_authHelper.currentUserCanAccessMilestone(milestone.get()))
			throw new ForbiddenException("You are not authorized to access this Milestone.");

		return milestone.get();
	}
	
	
	@PostMapping()
    public ResponseEntity<Milestone> createMilestone(@RequestBody WriteMilestoneDTO milestoneToCreate)
    		throws Exception
	{
		Milestone milestoneToPersist;
		Board milestoneBoard;
    	
    	if (milestoneToCreate.getTitle() == null)
    		throw new BadRequestException("Title is required.");
    	
    	if (milestoneToCreate.getBoardId() == null)
    		throw new BadRequestException("Board Id is required.");
    	
    	else
    	{
    		Optional<Board> board = _boardRepository.findById(milestoneToCreate.getBoardId());
    		
    		if (!board.isPresent())	
    			throw new NotFoundException("Board not found.");
    		
    		milestoneBoard = board.get();
    		
    		if (!_authHelper.currentUserCanAccessBoard(milestoneBoard))
    			throw new ForbiddenException("You are not authorized to create Milestones for this Board.");
    	}
    	
    	try	
    	{
    		milestoneToPersist = new Milestone(
				milestoneToCreate.getTitle(),
				milestoneToCreate.getDescription(),
				milestoneBoard,
				milestoneToCreate.getDueDate()
			);
    		
    		_milestoneRepository.save(milestoneToPersist);
    	}

    	catch (Exception e)
    	{
    		throw new InternalServerErrorException(e.getMessage());
    	}

    	
    	return new ResponseEntity<Milestone>(milestoneToPersist, HttpStatus.CREATED);
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<Milestone> updateMilestone(
		@PathVariable int id,
		@RequestBody WriteMilestoneDTO fieldsToUpdate
	) throws Exception
	{
		if (fieldsToUpdate.getTitle() != null && fieldsToUpdate.getTitle() == "")
    		throw new BadRequestException("Title cannot be empty.");
		
		Optional<Milestone> milestoneToUpdate = _milestoneRepository.findById(id);
		
		if (!milestoneToUpdate.isPresent())
			throw new NotFoundException("Milestone not found.");
		
		Milestone milestoneToPersist = milestoneToUpdate.get();
		
		if (!_authHelper.currentUserCanAccessMilestone(milestoneToPersist))
			throw new ForbiddenException("You are not authorized to access this Milestone.");
		
		if (fieldsToUpdate.getTitle() != null)
			milestoneToPersist.setTitle(fieldsToUpdate.getTitle());
		
		milestoneToPersist.setDescription(fieldsToUpdate.getDescription());
		milestoneToPersist.setDueDate(fieldsToUpdate.getDueDate());
		
		Milestone savedMilestone = _milestoneRepository.save(milestoneToPersist);		

		return new ResponseEntity<Milestone>(savedMilestone, HttpStatus.OK);
	}

	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ReadSuccessDTO> deleteMilestone(@PathVariable int id) throws Exception
	{
		ReadSuccessDTO response = new ReadSuccessDTO(false);
		
		Optional<Milestone> milestoneToDelete = _milestoneRepository.findById(id);
		
		if (!milestoneToDelete.isPresent())
			throw new NotFoundException("Milestone not found.");
		
		try
		{
			Set<Issue> issues = milestoneToDelete.get().getIssues();
			
			for (Issue issue: issues) 
			{ 
				issue.setMilestone(null);
				_issueRepository.save(issue);
			}
			
			_milestoneRepository.delete(milestoneToDelete.get());
			
			response.setSuccess(true);
		}
		
		catch (Exception e)
		{
			response.setSuccess(false);
		}
		
		return new ResponseEntity<ReadSuccessDTO>(response, HttpStatus.OK);
	}
}
