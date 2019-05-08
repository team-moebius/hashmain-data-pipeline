package com.moebius.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SignupDto {
	@NotNull
	private String name;
	@Email
	private String email;
	@NotNull
	private String password;
}
