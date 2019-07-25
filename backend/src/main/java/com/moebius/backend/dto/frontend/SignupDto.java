package com.moebius.backend.dto.frontend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class SignupDto {
	@NotNull
	private String name;
	@Email
	private String email;
	@NotNull
	private String password;
}
