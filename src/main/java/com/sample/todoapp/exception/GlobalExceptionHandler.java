package com.sample.todoapp.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<Object> handleExceptions( ResourceNotFoundException exception, WebRequest webRequest) {
//        ExceptionResponse response = new ExceptionResponse();
//        response.setDateTime(LocalDateTime.now());
//        response.setMessage("Not found");
//        ResponseEntity<Object> entity = new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
//        return entity;
//    }
	
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ExceptionResponse handleResourceNotFoundException() {
		ExceptionResponse Response = new ExceptionResponse();
		Response.setDateTime(LocalDateTime.now());
		Response.setMessage("Not found");
		return Response;
	}
	
	@ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ExceptionResponse handleUserNotFoundException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		ExceptionResponse exResponse = new ExceptionResponse();
		exResponse.setDateTime(LocalDateTime.now());
		exResponse.setMessage(ex.toString());
		return exResponse;
    }
}
