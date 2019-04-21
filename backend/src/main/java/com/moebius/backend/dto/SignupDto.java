package com.moebius.backend.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class SignupDto {
	@NotNull
	private String name;
	@Email
	private String email;
	@NotNull
	private String password;
}
