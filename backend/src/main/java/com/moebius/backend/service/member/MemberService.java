package com.moebius.backend.service.member;

import com.moebius.backend.assembler.MemberAssembler;
import com.moebius.backend.configuration.security.JwtUtil;
import com.moebius.backend.domain.members.MemberRepository;
import com.moebius.backend.dto.LoginDto;
import com.moebius.backend.dto.SignupDto;
import com.moebius.backend.model.MoebiusPrincipal;
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
import reactor.core.scheduler.Schedulers;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements ReactiveUserDetailsService {
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final MemberAssembler memberAssembler;

	public Mono<ResponseEntity<?>> createAccount(SignupDto signupDto) {
		Hooks.onOperatorDebug();

		return memberRepository.save(memberAssembler.toMember(signupDto))
			.map(member -> member != null ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build());
	}

	@Override
	public Mono<UserDetails> findByUsername(String email) {
		Hooks.onOperatorDebug();

		return memberRepository.findByEmail(email).switchIfEmpty(Mono.defer(() ->
			Mono.error(new UsernameNotFoundException("Email is not valid in moebius."))
		)).map(MoebiusPrincipal::new);
	}

	public Mono<ResponseEntity<?>> login(LoginDto loginDto) {
		Hooks.onOperatorDebug();

		return memberRepository.findByEmail(loginDto.getEmail())
			.subscribeOn(Schedulers.elastic())
			.publishOn(Schedulers.parallel())
			.map(member -> {
				if (passwordEncoder.encode(loginDto.getPassword()).equals(member.getPassword())) {
					return ResponseEntity.ok(JwtUtil.generateToken(new MoebiusPrincipal(member)));
				}
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			})
			.log();
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
