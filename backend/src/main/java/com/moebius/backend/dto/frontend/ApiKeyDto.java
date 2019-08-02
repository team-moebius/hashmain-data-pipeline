package com.moebius.backend.dto.frontend;

import com.moebius.backend.domain.commons.Exchange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class ApiKeyDto {
	@NotNull
	private ObjectId memberId;
	@NotNull
	private Exchange exchange;
	@NotBlank
	private String name;
	@NotBlank
	private String accessKey;
	@NotBlank
	private String secretKey;
}
