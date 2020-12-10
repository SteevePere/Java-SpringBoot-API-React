package com.quest.etna.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.quest.etna.model.dtos.issues.CreateIssueDTO;
import com.quest.etna.model.dtos.issues.UpdateIssueDTO;
import com.quest.etna.model.dtos.issues.UpdateIssueStatusDTO;
import com.quest.etna.model.entities.Board;
import com.quest.etna.model.entities.Issue;
import com.quest.etna.model.entities.Milestone;
import com.quest.etna.model.entities.User;
import com.quest.etna.repositories.BoardRepository;
import com.quest.etna.repositories.IssueRepository;
import com.quest.etna.repositories.MilestoneRepository;
import com.quest.etna.repositories.UserRepository;

import io.swagger.v3.oas.annotations.tags.Tag;


@RequestMapping("/issue")
@RestController
@Tag(name = "Issues", description = "Issue-related Endpoints.")
public class IssueController
{
	
	@Autowired
	private IssueRepository _issueRepository;
	
	@Autowired
	private MilestoneRepository _milestoneRepository;
	
	@Autowired
	private BoardRepository _boardRepository;
	
	@Autowired
	private UserRepository _userRepository;
	
	@Autowired
	private AuthHelper _authHelper;
	
	
	@GetMapping()
	public List<Issue> getIssues(@RequestParam("boardId") Optional<Integer> boardId) throws Exception
	{
		List<Issue> issues = new ArrayList<>();
		JwtUserDetails currentUser = _authHelper.getCurrentUser();
		
		if (currentUser.isAdmin())
		{
			if (boardId.isPresent())
				issues = _issueRepository.findByBoardId(boardId.get());
			
			else
				issues = (List<Issue>) _issueRepository.findAll();
		}
		
		else
		{
			if (boardId.isPresent())
				issues = _issueRepository.findByBoardAndUserId(boardId.get(), currentUser.getId());
			
			else
				issues = _issueRepository.findByUserId(currentUser.getId());
		}
		
		return issues;
	}
	
	
	@GetMapping("/{id}")
	public Issue getIssue(@PathVariable int id) throws Exception
	{
		Optional<Issue> issue = _issueRepository.findById(id);
		
		if (!issue.isPresent())		
			throw new NotFoundException("Issue not found.");
		
		if (!_authHelper.currentUserCanAccessIssue(issue.get()))
			throw new ForbiddenException("You are not authorized to access this Issue.");

		return issue.get();
	}
	
	
	@PostMapping()
    public ResponseEntity<Issue> createIssue(@RequestBody CreateIssueDTO issueToCreate) throws Exception
	{
		Issue issueToPersist;
		Board issueBoard;
		Milestone issueMilestone = null;
		User issueReporter;
		User issueAssignee = null;
    	
    	if (issueToCreate.getTitle() == null)
    		throw new BadRequestException("Title is required.");
    	
    	if (issueToCreate.getBoardId() == null)
    		throw new BadRequestException("Board Id is required.");
    	
    	else
    	{
    		Optional<Board> board = _boardRepository.findById(issueToCreate.getBoardId());
    		
    		if (!board.isPresent())	
    			throw new NotFoundException("Board not found.");
    		
    		issueBoard = board.get();
    		
    		if (!_authHelper.currentUserCanAccessBoard(issueBoard))
    			throw new ForbiddenException("You are not authorized to create Issues for this Board.");
    	}
    	
    	if (issueToCreate.getReporterId() == null)
    		throw new BadRequestException("Reporter Id is required.");
    	
    	else
    	{
    		Optional<User> reporter = _userRepository.findById(issueToCreate.getReporterId());
    		
    		if (!reporter.isPresent())	
    			throw new NotFoundException("Reporter not found.");
    		
    		issueReporter = reporter.get();
    	}
    	
    	if (issueToCreate.getMilestoneId() != null)
    	{
    		Optional<Milestone> milestone = _milestoneRepository
				.findById(issueToCreate.getMilestoneId());
    		
    		if (!milestone.isPresent())	
    			throw new NotFoundException("Milestone not found.");
    		
    		issueMilestone = milestone.get();
    	}
    	
    	if (issueToCreate.getAssigneeId() != null)
    	{
    		Optional<User> assignee = _userRepository.findById(issueToCreate.getAssigneeId());
    		
    		if (!assignee.isPresent())	
    			throw new NotFoundException("Assignee not found.");
    		
    		issueAssignee = assignee.get();
    	}
    	
    	try	
    	{
    		issueToPersist = new Issue(
				issueToCreate.getTitle(),
				issueToCreate.getDescription(),
				issueToCreate.getType(),
				issueToCreate.getStatus(),
				issueToCreate.getPriority(),
				issueBoard,
				issueMilestone,
				issueReporter,
				issueAssignee
			);
    		
    		_issueRepository.save(issueToPersist);
    	}

    	catch (Exception e)
    	{
    		throw new InternalServerErrorException(e.getMessage());
    	}
    	
    	return new ResponseEntity<Issue>(issueToPersist, HttpStatus.CREATED);
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<Issue> updateIssue(
		@PathVariable int id,
		@RequestBody UpdateIssueDTO fieldsToUpdate
	) throws Exception
	{
		if (fieldsToUpdate.getTitle() != null && fieldsToUpdate.getTitle() == "")
    		throw new BadRequestException("Title cannot be empty.");
		
		Optional<Issue> issueToUpdate = _issueRepository.findById(id);
		
		if (!issueToUpdate.isPresent())
			throw new NotFoundException("Issue not found.");
		
		Issue issueToPersist = issueToUpdate.get();
		
		if (!_authHelper.currentUserCanAccessIssue(issueToPersist))
			throw new ForbiddenException("You are not authorized to access this Issue.");
		
		if (fieldsToUpdate.getTitle() != null)
			issueToPersist.setTitle(fieldsToUpdate.getTitle());
		
		issueToPersist.setDescription(fieldsToUpdate.getDescription());
		
		if (fieldsToUpdate.getType() != null)
			issueToPersist.setType(fieldsToUpdate.getType());
		
		if (fieldsToUpdate.getStatus() != null)
			issueToPersist.setStatus(fieldsToUpdate.getStatus());
		
		if (fieldsToUpdate.getPriority() != null)
			issueToPersist.setPriority(fieldsToUpdate.getPriority());
		
		if (fieldsToUpdate.getMilestoneId() != null)
		{
			Optional<Milestone> newMilestone = _milestoneRepository
					.findById(fieldsToUpdate.getMilestoneId());
			
			if (!newMilestone.isPresent())
				throw new NotFoundException("Milestone not found.");
			
			Milestone newMilestoneToPersist = newMilestone.get();
			
			issueToPersist.setMilestone(newMilestoneToPersist);
		}
		
		else 
			issueToPersist.setMilestone(null);
		
		if (fieldsToUpdate.getAssigneeId() != null)
		{
			Optional<User> newAssignee = _userRepository
					.findById(fieldsToUpdate.getAssigneeId());
			
			if (!newAssignee.isPresent())
				throw new NotFoundException("Assignee not found.");
			
			User newAssigneeToPersist = newAssignee.get();
			
			issueToPersist.setAssignee(newAssigneeToPersist);
		}
		
		else 
			issueToPersist.setAssignee(null);
		
		Issue savedIssue = _issueRepository.save(issueToPersist);		

		return new ResponseEntity<Issue>(savedIssue, HttpStatus.OK);
	}
	
	
	@PatchMapping("/status/{id}")
	public ResponseEntity<Issue> updateIssueStatus(
		@PathVariable int id,
		@RequestBody UpdateIssueStatusDTO fieldsToUpdate
	) throws Exception
	{
		Optional<Issue> issueToUpdate = _issueRepository.findById(id);
		
		if (!issueToUpdate.isPresent())
			throw new NotFoundException("Issue not found.");
		
		Issue issueToPersist = issueToUpdate.get();
		
		if (!_authHelper.currentUserCanAccessIssue(issueToPersist))
			throw new ForbiddenException("You are not authorized to access this Issue.");
		
		if (fieldsToUpdate.getStatus() != null)
			issueToPersist.setStatus(fieldsToUpdate.getStatus());
		
		Issue savedIssue = _issueRepository.save(issueToPersist);		

		return new ResponseEntity<Issue>(savedIssue, HttpStatus.OK);
	}

	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ReadSuccessDTO> deleteIssue(@PathVariable int id) throws Exception
	{
		ReadSuccessDTO response = new ReadSuccessDTO(false);
		
		Optional<Issue> issueToDelete = _issueRepository.findById(id);
		
		if (!issueToDelete.isPresent())
			throw new NotFoundException("Issue not found.");
		
		try
		{
			_issueRepository.delete(issueToDelete.get());
			response.setSuccess(true);
		}
		
		catch (Exception e)
		{
			response.setSuccess(false);
		}
		
		return new ResponseEntity<ReadSuccessDTO>(response, HttpStatus.OK);
	}
}
