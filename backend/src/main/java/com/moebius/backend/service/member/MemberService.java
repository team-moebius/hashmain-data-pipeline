package com.moebius.backend.service.member;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.domain.members.MemberRepository;
import com.moebius.backend.dto.MemberDto;
import com.moebius.backend.model.AccountResponse;
import com.moebius.backend.model.MoebiusPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class MemberService implements ReactiveUserDetailsService {

	private static final String AUTH_RESPONSE_HEADER_NAME = "Moebius-Authorization";

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Mono<Member> findByName(String name) {
		return memberRepository.findByName(name);
	}

	public Mono<AccountResponse> createAccount() {
		return Mono.just(new AccountResponse());
	}

	@Override
	public Mono<UserDetails> findByUsername(String email) {
		return memberRepository.findByEmail(email).switchIfEmpty(Mono.defer(() ->
			Mono.error(new UsernameNotFoundException("Email is not valid in moebius."))
		)).map(MoebiusPrincipal::new);
	}

	public void addAuthHeader(ServerHttpResponse response, Member member) {
		log.debug("Start to add auth header for " + member.getEmail());

		response.getHeaders().add(AUTH_RESPONSE_HEADER_NAME, UUID.randomUUID().toString());
	}

	public Mono<ResponseEntity<?>> login(MemberDto memberDto) {
//		ReactiveSecurityContextHolder.getContext()
//				.map(SecurityContext::getAuthentication)
//				.map(Authentication::getPrincipal)
//				.cast(MoebiusPrincipal.class)
//				.doOnNext(MoebiusPrincipal::eraseCredentials)
//				.map(MoebiusPrincipal::currentMember)
//				.zipWith(serverWebExchange.getFormData()).
//				doOnNext(tuple -> memberService.addAuthHeader(serverWebExchange.getResponse(), tuple.getT1()))
//				.map(tuple -> modelMapper.map(tuple.getT1(), MemberDto .class));
		return null;
	}
}
