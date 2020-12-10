package com.quest.etna.model.dtos.issues;

import com.quest.etna.model.enums.IssueStatus;


public class UpdateIssueStatusDTO
{
	
	private IssueStatus status;

	
	public IssueStatus getStatus()
	{
		return status;
	}

	
	public void setStatus(IssueStatus status)
	{
		this.status = status;
	}
}
