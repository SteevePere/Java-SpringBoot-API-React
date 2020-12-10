package com.quest.etna.model.dtos.metrics;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.quest.etna.model.enums.IssuePriority;
import com.quest.etna.model.enums.IssueStatus;
import com.quest.etna.model.enums.IssueType;


public class ReadMetricsDTO implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	private Map<IssueStatus, Long> numberOfIssuesByStatus = new HashMap<IssueStatus, Long>();
	private Map<IssueType, Long> numberOfIssuesByType = new HashMap<IssueType, Long>();
	private Map<IssuePriority, Long> numberOfIssuesByPriority = new HashMap<IssuePriority, Long>();
	
	private Map<String, Integer> numberOfUsersByBoard = new HashMap<String, Integer>();
	
	private Long activeUsers;
	private Long inactiveUsers;
	private Long assignedIssues;
	private Long unassignedIssues;
	private Long notOverdueMilestones;
	private Long overdueMilestones;
	
	public Map<IssueStatus, Long> getNumberOfIssuesByStatus()
	{
		return numberOfIssuesByStatus;
	}


	public void setNumberOfIssuesByStatus(Map<IssueStatus, Long> numberOfIssuesByStatus)
	{
		this.numberOfIssuesByStatus = numberOfIssuesByStatus;
	}


	public Map<IssueType, Long> getNumberOfIssuesByType()
	{
		return numberOfIssuesByType;
	}


	public void setNumberOfIssuesByType(Map<IssueType, Long> numberOfIssuesByType)
	{
		this.numberOfIssuesByType = numberOfIssuesByType;
	}


	public Map<IssuePriority, Long> getNumberOfIssuesByPriority()
	{
		return numberOfIssuesByPriority;
	}


	public void setNumberOfIssuesByPriority(Map<IssuePriority, Long> numberOfIssuesByPrority)
	{
		this.numberOfIssuesByPriority = numberOfIssuesByPrority;
	}


	public Long getActiveUsers()
	{
		return activeUsers;
	}


	public void setActiveUsers(Long activeUsers)
	{
		this.activeUsers = activeUsers;
	}


	public Long getInactiveUsers()
	{
		return inactiveUsers;
	}


	public void setInactiveUsers(Long inactiveUsers)
	{
		this.inactiveUsers = inactiveUsers;
	}
	

	public Long getAssignedIssues()
	{
		return assignedIssues;
	}


	public void setAssignedIssues(Long assignedIssues)
	{
		this.assignedIssues = assignedIssues;
	}


	public Long getUnassignedIssues()
	{
		return unassignedIssues;
	}


	public void setUnassignedIssues(Long unassignedIssues)
	{
		this.unassignedIssues = unassignedIssues;
	}


	public Map<String, Integer> getNumberOfUsersByBoard()
	{
		return numberOfUsersByBoard;
	}


	public void setNumberOfUsersByBoard(Map<String, Integer> numberOfUsersByBoard)
	{
		this.numberOfUsersByBoard = numberOfUsersByBoard;
	}


	public Long getNotOverdueMilestones()
	{
		return notOverdueMilestones;
	}


	public void setNotOverdueMilestones(Long notOverdueMilestones)
	{
		this.notOverdueMilestones = notOverdueMilestones;
	}


	public Long getOverdueMilestones()
	{
		return overdueMilestones;
	}


	public void setOverdueMilestones(Long overdueMilestones)
	{
		this.overdueMilestones = overdueMilestones;
	}


	@Override
	public String toString() {
		return "ReadMetricsDTO [numberOfIssuesByStatus=" + numberOfIssuesByStatus + ", numberOfIssuesByType="
				+ numberOfIssuesByType + ", numberOfIssuesByPriority=" + numberOfIssuesByPriority
				+ ", numberOfUsersByBoard=" + numberOfUsersByBoard + ", activeUsers=" + activeUsers + ", inactiveUsers="
				+ inactiveUsers + ", assignedIssues=" + assignedIssues + ", unassignedIssues=" + unassignedIssues
				+ ", notOverdueMilestones=" + notOverdueMilestones + ", overdueMilestones=" + overdueMilestones + "]";
	}
}
