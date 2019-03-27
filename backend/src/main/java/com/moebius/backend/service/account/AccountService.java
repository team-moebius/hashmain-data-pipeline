package com.moebius.backend.service.account;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.domain.members.MemberRepository;
import com.moebius.backend.model.AccountResponse;
import com.moebius.backend.model.MoebiusPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
public class AccountService implements ReactiveUserDetailsService {

	private static final String AUTH_RESPONSE_HEADER_NAME = "Moebius-Authorization";

	private final MemberRepository memberRepository;

	public AccountService(MemberRepository memberRepository) {
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
}
