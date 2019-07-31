package com.moebius.backend.dto.frontend;

import com.moebius.backend.domain.commons.Exchange;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Getter
@Setter
@ToString
public class ApiKeyDto {
	private ObjectId memberId;
	private Exchange exchange;
	private String name;
	private String accessKey;
	private String secretKey;
}
