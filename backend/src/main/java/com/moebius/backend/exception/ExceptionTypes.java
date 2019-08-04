package com.moebius.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionTypes {
	WRONG_PASSWORD("Password is wrong."),
	INVALID_EMAIL("Email is not valid in moebius."),
	INVALID_CODE("Entered code is not valid in moebius."),

	UNVERIFIED_DATA("The data is not verified.") {
		@Override
		public String getMessage(String data) {
			return data + " is not verified.";
		}
	},
	ALREADY_VERIFIED_DATA("The data already verified.") {
		@Override
		public String getMessage(String data) {
			return data + " is already verified.";
		}
	},
	DUPLICATE_DATA("The data already exists.") {
		@Override
		public String getMessage(String data) {
			return data + " already exists.";
		}
	},
	NONEXISTENT_DATA("The data is not found.") {
		@Override
		public String getMessage(String data) {
			return data + " is not found.";
		}
	};

	private String message;

	public String getMessage(String data) {
		return message;
	}
}
