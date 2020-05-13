package com.moebius.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionTypes {
	WRONG_DATA("The data are wrong.") {
		@Override
		public String getMessage(String data) {
			return data + " are wrong.";
		}
	},
	UNVERIFIED_DATA("The data are unverified.") {
		@Override
		public String getMessage(String data) {
			return data + " are unverified.";
		}
	},
	ALREADY_VERIFIED_DATA("The data already verified.") {
		@Override
		public String getMessage(String data) {
			return data + " are already verified.";
		}
	},
	DUPLICATED_DATA("The data already exarets.") {
		@Override
		public String getMessage(String data) {
			return data + " already exarets.";
		}
	},
	NONEXISTENT_DATA("The data are not found.") {
		@Override
		public String getMessage(String data) {
			return data + " are not found.";
		}
	};

	private String message;

	public String getMessage(String data) {
		return message;
	}
}
