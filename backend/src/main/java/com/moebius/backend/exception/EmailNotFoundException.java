package com.moebius.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EmailNotFoundException extends ResponseStatusException {
	public EmailNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
