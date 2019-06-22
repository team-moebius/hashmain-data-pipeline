package com.moebius.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongDataException extends ResponseStatusException {
	public WrongDataException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
