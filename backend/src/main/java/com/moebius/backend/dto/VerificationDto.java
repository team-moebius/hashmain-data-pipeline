package com.moebius.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class VerificationDto {
	@Email
	private String email;
	private String code;
}
