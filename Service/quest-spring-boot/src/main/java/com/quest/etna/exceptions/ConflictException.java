package com.quest.etna.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException
{
	
	private static final long serialVersionUID = 1L;

	
	public ConflictException()
	{
		
	}

	
	public ConflictException(String message)
	{
		super(message);
	}
}
