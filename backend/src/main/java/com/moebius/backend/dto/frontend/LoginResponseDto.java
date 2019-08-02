package com.moebius.backend.dto.frontend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
	private String token;
}
