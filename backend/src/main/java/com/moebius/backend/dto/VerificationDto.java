package com.moebius.backend.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;

@Getter
@Setter
@ToString
public class VerificationDto {
	@Email
	@NonNull
	private String email;
	@NonNull
	private String code;
}
