package com.moebius.backend.dto;

import lombok.*;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class LoginDto {
	@Email
	@NonNull
	private String email;

	@NonNull
	private String password;
}
