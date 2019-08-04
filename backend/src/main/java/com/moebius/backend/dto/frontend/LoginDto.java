package com.moebius.backend.dto.frontend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginDto {
	@Email
	private String email;

	@NotBlank
	private String password;
}
