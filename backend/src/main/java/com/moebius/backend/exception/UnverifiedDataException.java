package com.moebius.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnverifiedDataException extends ResponseStatusException {
	public UnverifiedDataException(String message) {
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
