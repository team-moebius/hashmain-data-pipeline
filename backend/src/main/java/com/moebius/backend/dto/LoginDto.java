package com.moebius.backend.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Builder
public class LoginDto {
	@Email
	private String email;
	@NotNull
	private String password;
}
