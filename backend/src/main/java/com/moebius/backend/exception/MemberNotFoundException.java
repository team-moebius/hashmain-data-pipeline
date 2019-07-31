package com.moebius.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class MemberNotFoundException extends ResponseStatusException {
	public MemberNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
