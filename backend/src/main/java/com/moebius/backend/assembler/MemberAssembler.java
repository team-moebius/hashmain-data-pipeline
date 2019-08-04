package com.moebius.backend.assembler;

import com.moebius.backend.domain.members.Level;
import com.moebius.backend.domain.members.Member;
import com.moebius.backend.domain.members.Role;
import com.moebius.backend.dto.frontend.MemberDto;
import com.moebius.backend.dto.frontend.SignupDto;
import com.moebius.backend.utils.Verifier;
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
		Verifier.checkNullFields(signupDto);

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

	public MemberDto toDto(@NotNull Member member) {
		Verifier.checkNullFields(member);

		MemberDto dto = new MemberDto();
		dto.setEmail(member.getEmail());
		dto.setName(member.getName());
		dto.setLevel(member.getLevel() != null ? member.getLevel() : Level.NORMAL);

		return dto;
	}
}
