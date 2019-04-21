package com.moebius.backend.assembler;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.dto.SignupDto;
import org.springframework.stereotype.Component;

@Component
public class MemberAssembler {
	public Member toMember(SignupDto signupDto) {
		return new Member()
			.setName(signupDto.getName())
			.setEmail(signupDto.getEmail())
			.setPassword(signupDto.getPassword());
	}
}
