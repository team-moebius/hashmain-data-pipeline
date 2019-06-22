package com.moebius.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class VerificationFailedException extends ResponseStatusException {
	public VerificationFailedException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
