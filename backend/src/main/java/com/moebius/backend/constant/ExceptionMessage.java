package com.moebius.backend.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {
	WRONG_PASSWORD("Password is wrong."),
	INVALID_EMAIL("Email is not valid in moebius.");

	private String message;
}
