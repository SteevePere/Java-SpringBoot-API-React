package com.quest.etna.model.dtos.general;

import java.io.Serializable;


public class ReadSuccessDTO implements Serializable
{
	
	private static final long serialVersionUID = 5926468583005150707L;
	
	private Boolean success;

	
	public ReadSuccessDTO()
	{
		
	}
	
	
	public ReadSuccessDTO(Boolean success)
	{
		this.success = success;
	}
	
	
	public Boolean getSuccess()
	{
		return this.success;
	}

	
	public void setSuccess(Boolean success)
	{
		this.success = success;
	}	
}
