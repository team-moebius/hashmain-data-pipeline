package com.moebius.backend.dto.frontend;

import com.moebius.backend.domain.members.Level;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class MemberDto {
	@Id
	private String id;
	private Level level;
	@NotBlank
	private String name;
	@Email
	private String email;
}
