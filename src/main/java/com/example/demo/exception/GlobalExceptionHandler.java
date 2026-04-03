package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDuplicate(DataIntegrityViolationException ex,
			HttpServletRequest request) {
		String message = "Duplicate data";

		if (ex.getMostSpecificCause().getMessage().contains("email_UNIQUE")) {
			message = "Email already exists";
		}

		ErrorResponse error = ErrorResponse.builder().message(message).status(HttpStatus.BAD_REQUEST.value())
				.path(request.getRequestURI()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorResponse> handleRuntime(RuntimeException ex, HttpServletRequest request) {

		ErrorResponse error = ErrorResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST.value())
				.path(request.getRequestURI()).timestamp(LocalDateTime.now()).build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}