package com.moebius.backend.assembler;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class MemberAssembler {
	private final PasswordEncoder passwordEncoder;

	public Member toMember(SignupDto signupDto) {
		Member member = new Member();
		member.setName(signupDto.getName());
		member.setEmail(signupDto.getEmail());
		member.setPassword(passwordEncoder.encode(signupDto.getPassword()));
		member.setCreatedAt(LocalDateTime.now());
		member.setUpdatedAt(LocalDateTime.now());

		return member;
	}
}
