package com.retroexchanges.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationErrorException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public AuthenticationErrorException(String message) {
        super(message);
    }
}
