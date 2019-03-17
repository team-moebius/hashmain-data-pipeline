package com.moebius.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;

@Getter
@Setter
@Accessors(chain = true)
public class UserDto {
	@Email
	private String email;
	private String password;
}
