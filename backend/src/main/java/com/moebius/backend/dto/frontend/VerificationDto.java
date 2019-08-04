package com.moebius.backend.dto.frontend;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class VerificationDto {
	@Email
	private String email;
	@NotNull
	private String code;
}
