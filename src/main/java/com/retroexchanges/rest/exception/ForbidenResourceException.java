package com.retroexchanges.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbidenResourceException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public ForbidenResourceException(String message) {
        super(message);
    }
}
