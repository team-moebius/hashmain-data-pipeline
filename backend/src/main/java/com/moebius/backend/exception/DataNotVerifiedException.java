package com.moebius.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DataNotVerifiedException extends ResponseStatusException {
	public DataNotVerifiedException(String message) {
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
