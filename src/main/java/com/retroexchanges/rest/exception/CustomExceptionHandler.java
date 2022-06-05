package com.retroexchanges.rest.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler 
{
	private String INCORRECT_REQUEST = "INCORRECT_REQUEST";
	private String NOT_FOUND = "NOT_FOUND";
	private String CONFLICT = "CONFLICT";
	private String UNAUTHORIZED= "UNAUTHORIZED"; 
	private String BAD_REQUEST = "BAD_REQUEST";
	private String FORBIDDEN = "FORBIDDEN";
	
	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<ErrorResponse> handleForbidenErrorException
						(BadRequestException ex, WebRequest request) 
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ForbidenResourceException.class)
	public final ResponseEntity<ErrorResponse> handleForbidenErrorException
						(ForbidenResourceException ex, WebRequest request) 
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(FORBIDDEN, details);
		return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(AuthenticationErrorException.class)
	public final ResponseEntity<ErrorResponse> handleAuthenticationErrorException
						(AuthenticationErrorException ex, WebRequest request) 
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(UNAUTHORIZED, details);
		return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(RecordAlreadyExistException.class)
	public final ResponseEntity<ErrorResponse> handleUserAlreadyExistException
						(RecordAlreadyExistException ex, WebRequest request) 
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(CONFLICT, details);
		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<ErrorResponse> handleUserNotFoundException
						(RecordNotFoundException ex, WebRequest request) 
	{
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(NOT_FOUND, details);
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MissingHeaderInfoException.class)
	public final ResponseEntity<ErrorResponse> handleInvalidTraceIdException
						(MissingHeaderInfoException ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<ErrorResponse> handleConstraintViolation(
											ConstraintViolationException ex,
											WebRequest request)
	{
		List<String> details = ex.getConstraintViolations()
									.parallelStream()
									.map(e -> e.getMessage())
									.collect(Collectors.toList());

		ErrorResponse error = new ErrorResponse(BAD_REQUEST, details);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
}
