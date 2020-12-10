package com.quest.etna.model.dtos.milestones;

import java.util.Date;


public class UpdateMilestoneDTO
{

	private String title;
	private String description;
	private Integer boardId;
	private Date dueDate;

	
	public String getTitle()
	{
		return this.title;
	}
	
	
	public void setTitle(String title)
	{
		this.title = title;
	}


	public String getDescription()
	{
		return description;
	}


	public void setDescription(String description)
	{
		this.description = description;
	}


	public Integer getBoardId()
	{
		return boardId;
	}


	public void setBoardId(Integer boardId)
	{
		this.boardId = boardId;
	}


	public Date getDueDate()
	{
		return dueDate;
	}


	public void setDueDate(Date dueDate)
	{
		this.dueDate = dueDate;
	}
}