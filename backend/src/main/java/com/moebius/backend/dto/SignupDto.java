package com.moebius.backend.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class SignupDto {
	@NonNull
	private String name;

	@Email
	@NonNull
	private String email;

	@NonNull
	private String password;
}
