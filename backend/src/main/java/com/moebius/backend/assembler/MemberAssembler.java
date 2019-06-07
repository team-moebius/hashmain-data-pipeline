package com.moebius.backend.assembler;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.domain.members.Role;
import com.moebius.backend.dto.SignupDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class MemberAssembler {
	private final PasswordEncoder passwordEncoder;

	public Member toMember(@NonNull SignupDto signupDto) {
		Role role = new Role();
		role.setId("MEMBER");

		Set<Role> roles = new HashSet<>();
		roles.add(role);

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
