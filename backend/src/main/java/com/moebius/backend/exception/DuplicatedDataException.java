package com.moebius.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicatedDataException extends ResponseStatusException {
	public DuplicatedDataException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
