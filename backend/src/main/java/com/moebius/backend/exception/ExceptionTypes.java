package com.moebius.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionTypes {
	WRONG_DATA("The data is wrong.") {
		@Override
		public String getMessage(String data) {
			return data + " is wrong.";
		}
	},
	UNVERIFIED_DATA("The data is unverified.") {
		@Override
		public String getMessage(String data) {
			return data + " is unverified.";
		}
	},
	ALREADY_VERIFIED_DATA("The data already verified.") {
		@Override
		public String getMessage(String data) {
			return data + " is already verified.";
		}
	},
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
