package com.suru.fts.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.suru.fts.api.exception.ResourceNotFoundException;

public class ApiControllerBase {

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public String handleResourceNotFoundException(final ResourceNotFoundException ex) {
		return ex.getMessage();
	}

}