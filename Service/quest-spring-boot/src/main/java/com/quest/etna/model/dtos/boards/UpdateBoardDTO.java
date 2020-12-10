package com.quest.etna.model.dtos.boards;

import java.util.Set;


public class UpdateBoardDTO
{

	private String title;
	private Set<Integer> user_ids;

	
	public String getTitle()
	{
		return this.title;
	}
	
	
	public void setTitle(String title)
	{
		this.title = title;
	}


	public Set<Integer> getUserIds()
	{
		return user_ids;
	}


	public void setUserIds(Set<Integer> userIds)
	{
		this.user_ids = userIds;
	}
}