package com.moebius.backend.service.member;

import com.moebius.backend.assembler.MemberAssembler;
import com.moebius.backend.configuration.security.JwtUtil;
import com.moebius.backend.domain.members.MemberRepository;
import com.moebius.backend.dto.LoginDto;
import com.moebius.backend.dto.SignupDto;
import com.moebius.backend.model.MoebiusPrincipal;
import com.moebius.backend.service.common.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Hooks;
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
	private final EmailService emailService;

	public Mono<ResponseEntity<?>> createAccount(SignupDto signupDto) {
		Hooks.onOperatorDebug();

		return memberRepository.save(memberAssembler.toMember(signupDto))
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(member -> member != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build());
	}

	@Override
	public Mono<UserDetails> findByUsername(String email) {
		Hooks.onOperatorDebug();

		return memberRepository.findByEmail(email)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new UsernameNotFoundException("Email is not valid in moebius."))))
			.map(MoebiusPrincipal::new);
	}

	public Mono<ResponseEntity<?>> login(LoginDto loginDto) {
		Hooks.onOperatorDebug();

		return memberRepository.findByEmail(loginDto.getEmail())
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.map(member -> {
				if (passwordEncoder.encode(loginDto.getPassword()).equals(member.getPassword())) {
					return ResponseEntity.ok(JwtUtil.generateToken(new MoebiusPrincipal(member)));
				}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			})
			.log();
	}

	public Mono<ResponseEntity<?>> findPassword(String email) {
		Hooks.onOperatorDebug();

		return emailService.findPassword(email);
	}
	// Reference
//	public Mono<ResponseEntity<?>> login(LoginDto loginDto) {
//		ReactiveSecurityContextHolder.getContext()
//				.map(SecurityContext::getAuthentication)
//				.map(Authentication::getPrincipal)
//				.cast(MoebiusPrincipal.class)
//				.doOnNext(MoebiusPrincipal::eraseCredentials)
//				.map(MoebiusPrincipal::currentMember)
//				.zipWith(serverWebExchange.getFormData()).
//				doOnNext(tuple -> memberService.addAuthHeader(serverWebExchange.getResponse(), tuple.getT1()))
//				.map(tuple -> modelMapper.map(tuple.getT1(), MemberDto .class));
//		return null;
//	}
}
