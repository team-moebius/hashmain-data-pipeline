package com.moebius.backend.dto.frontend.response;

import com.moebius.backend.domain.commons.Exchange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ApiKeyResponseDto {
	@Id
	private String id;
	@NotNull
	private Exchange exchange;
	@NotBlank
	private String name;
	@NotBlank
	private String accessKey;
	@NotBlank
	private String secretKey;
}
