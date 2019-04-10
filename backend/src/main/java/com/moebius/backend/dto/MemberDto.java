package com.moebius.backend.dto;

import com.moebius.backend.domain.members.ApiKey;
import com.moebius.backend.domain.members.Level;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import java.util.Set;

@Getter
@Builder
public class MemberDto {
	private Level level;
	private String name;
	@Email
	private String email;
	private Set<ApiKey> apiKeys;
}
