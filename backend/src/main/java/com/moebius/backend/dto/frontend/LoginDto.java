package com.moebius.backend.dto.frontend;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class LoginDto {
	@Email
	private String email;

	@NotNull
	private String password;
}
