package com.moebius.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionMessage {
	WRONG_PASSWORD("Password is wrong."),
	INVALID_EMAIL("Email is not valid in moebius."),
	DUPLICATED_DATA("The data already exists.") {
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
