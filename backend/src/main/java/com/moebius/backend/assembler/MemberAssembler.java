package com.moebius.backend.assembler;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.domain.members.Role;
import com.moebius.backend.dto.frontend.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MemberAssembler {
	private static final String ROLE = "MEMBER";
	private final PasswordEncoder passwordEncoder;

	public Member toMember(@NotNull SignupDto signupDto) {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(ROLE));

		Member member = new Member();
		member.setName(signupDto.getName());
		member.setEmail(signupDto.getEmail());
		member.setPassword(passwordEncoder.encode(signupDto.getPassword()));
		member.setRoles(roles);
		member.setCreatedAt(LocalDateTime.now());
		member.setUpdatedAt(LocalDateTime.now());

		return member;
	}
}
