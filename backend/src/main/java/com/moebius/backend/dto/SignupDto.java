package com.moebius.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class SignupDto {
	private String name;
	@Email
	private String email;
	private String password;
}
