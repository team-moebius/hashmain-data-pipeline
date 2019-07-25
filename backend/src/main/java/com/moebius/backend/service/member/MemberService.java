package com.moebius.backend.service.member;

import com.moebius.backend.assembler.MemberAssembler;
import com.moebius.backend.configuration.security.JwtUtil;
import com.moebius.backend.domain.members.MemberRepository;
import com.moebius.backend.domain.members.MoebiusPrincipal;
import com.moebius.backend.dto.frontend.LoginDto;
import com.moebius.backend.dto.frontend.SignupDto;
import com.moebius.backend.exception.DuplicateDataException;
import com.moebius.backend.exception.EmailNotFoundException;
import com.moebius.backend.exception.ExceptionTypes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements ReactiveUserDetailsService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberAssembler memberAssembler;

	@Override
	public Mono<UserDetails> findByUsername(String email) {
		return memberRepository.findByEmail(email)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException(ExceptionTypes.INVALID_EMAIL.getMessage()))))
			.map(MoebiusPrincipal::new);
	}

	public Mono<ResponseEntity<?>> createAccount(SignupDto signupDto) {
		return memberRepository.save(memberAssembler.toMember(signupDto))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.onErrorMap(exception -> exception instanceof DuplicateKeyException ?
				new DuplicateDataException(ExceptionTypes.DUPLICATED_DATA.getMessage(signupDto.getEmail())) :
				exception)
			.map(member -> ResponseEntity.ok(HttpStatus.OK.getReasonPhrase()));
	}

	public Mono<ResponseEntity<String>> login(LoginDto loginDto) {
		return memberRepository.findByEmail(loginDto.getEmail())
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(
				Mono.defer(() -> Mono.error(new EmailNotFoundException(ExceptionTypes.NONEXISTENT_DATA.getMessage(loginDto.getEmail())))))
			.map(member -> passwordEncoder.matches(loginDto.getPassword(), member.getPassword()) ?
				ResponseEntity.ok(JwtUtil.generateToken(new MoebiusPrincipal(member))) :
				ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionTypes.WRONG_PASSWORD.getMessage())
			);
	}
}
