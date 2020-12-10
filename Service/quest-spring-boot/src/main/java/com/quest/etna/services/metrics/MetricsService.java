package com.quest.etna.services.metrics;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.quest.etna.model.dtos.metrics.ReadMetricsDTO;
import com.quest.etna.model.entities.Board;
import com.quest.etna.model.entities.Issue;
import com.quest.etna.model.entities.Milestone;
import com.quest.etna.model.entities.User;
import com.quest.etna.model.enums.IssuePriority;
import com.quest.etna.model.enums.IssueStatus;
import com.quest.etna.model.enums.IssueType;
import com.quest.etna.repositories.BoardRepository;
import com.quest.etna.repositories.IssueRepository;
import com.quest.etna.repositories.MilestoneRepository;
import com.quest.etna.repositories.UserRepository;


@Service
public class MetricsService
{
	
	@Autowired
	private UserRepository _userRepository;
	
	@Autowired
	private BoardRepository _boardRepository;
	
	@Autowired
	private MilestoneRepository _milestoneRepository;

	@Autowired
	private IssueRepository _issueRepository;
	
	
	public ReadMetricsDTO getMetricsObject()
	{
		ReadMetricsDTO metrics = new ReadMetricsDTO();
		
		List<User> users = (List<User>) _userRepository.findAll();
		List<Board> boards = (List<Board>) _boardRepository.findAll();
		List<Milestone> milestones = (List<Milestone>) _milestoneRepository.findAll();
		List<Issue> issues = (List<Issue>) _issueRepository.findAll();
		
		Long activeUsers = users.stream()
			.filter(user -> user.getIsActive() == true)
			.count();
		
		Long inactiveUsers = users.stream()
				.filter(user -> user.getIsActive() == false)
				.count();
		
		Long assignedIssues = issues.stream()
				.filter(issue -> issue.getAssignee() != null)
				.count();
		
		Long unassignedIssues = issues.stream()
				.filter(issue -> issue.getAssignee() == null)
				.count();
		
		metrics.setActiveUsers(activeUsers);
		metrics.setInactiveUsers(inactiveUsers);
		metrics.setAssignedIssues(assignedIssues);
		metrics.setUnassignedIssues(unassignedIssues);
		
		Map<IssueStatus, Long> numberOfIssuesByStatus = new HashMap<IssueStatus, Long>();
		Map<IssueType, Long> numberOfIssuesByType = new HashMap<IssueType, Long>();
		Map<IssuePriority, Long> numberOfIssuesByPriority = new HashMap<IssuePriority, Long>();
		
		Long openIssues = issues.stream()
				.filter(issue -> issue.getStatus() == IssueStatus.OPEN)
				.count();
		
		Long inProgressIssues = issues.stream()
				.filter(issue -> issue.getStatus() == IssueStatus.IN_PROGRESS)
				.count();
		
		Long testingIssues = issues.stream()
				.filter(issue -> issue.getStatus() == IssueStatus.TESTING)
				.count();
		
		Long deployedIssues = issues.stream()
				.filter(issue -> issue.getStatus() == IssueStatus.DEPLOYED)
				.count();
		
		Long closedIssues = issues.stream()
				.filter(issue -> issue.getStatus() == IssueStatus.CLOSED)
				.count();
		
		numberOfIssuesByStatus.put(IssueStatus.OPEN, openIssues);
		numberOfIssuesByStatus.put(IssueStatus.IN_PROGRESS, inProgressIssues);
		numberOfIssuesByStatus.put(IssueStatus.TESTING, testingIssues);
		numberOfIssuesByStatus.put(IssueStatus.DEPLOYED, deployedIssues);
		numberOfIssuesByStatus.put(IssueStatus.CLOSED, closedIssues);
		
		metrics.setNumberOfIssuesByStatus(numberOfIssuesByStatus);
		
		Long stories = issues.stream()
				.filter(issue -> issue.getType() == IssueType.STORY)
				.count();
		
		Long bugs = issues.stream()
				.filter(issue -> issue.getType() == IssueType.BUG)
				.count();
		
		numberOfIssuesByType.put(IssueType.STORY, stories);
		numberOfIssuesByType.put(IssueType.BUG, bugs);
		
		metrics.setNumberOfIssuesByType(numberOfIssuesByType);
		
		Long lowPriority = issues.stream()
				.filter(issue -> issue.getPriority() == IssuePriority.LOW)
				.count();
		
		Long mediumPriority = issues.stream()
				.filter(issue -> issue.getPriority() == IssuePriority.MEDIUM)
				.count();
		
		Long highPriority = issues.stream()
				.filter(issue -> issue.getPriority() == IssuePriority.HIGH)
				.count();
		
		numberOfIssuesByPriority.put(IssuePriority.LOW, lowPriority);
		numberOfIssuesByPriority.put(IssuePriority.MEDIUM, mediumPriority);
		numberOfIssuesByPriority.put(IssuePriority.HIGH, highPriority);
		
		metrics.setNumberOfIssuesByPriority(numberOfIssuesByPriority);
		
		Map<String, Integer> numberOfUsersByBoard = new HashMap<String, Integer>();
		
		for (Board board : boards) numberOfUsersByBoard.put(board.getTitle(), board.getNumberOfUsers());
		
		metrics.setNumberOfUsersByBoard(numberOfUsersByBoard);
		
		Long overdueMilestones = milestones.stream()
				.filter(milestone ->
					milestone.getDueDate() != null && milestone.getDueDate().before(new Date()))
				.count();
		
		Long notOverdueMilestones = milestones.stream()
				.filter(milestone -> 
					milestone.getDueDate() == null || milestone.getDueDate().after(new Date()))
				.count();
		
		metrics.setOverdueMilestones(overdueMilestones);
		metrics.setNotOverdueMilestones(notOverdueMilestones);
		
		return metrics;
	}
}
