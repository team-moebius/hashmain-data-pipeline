package com.moebius.backend.service.account;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.domain.members.MemberRepository;
import com.moebius.backend.model.AccountResponse;
import com.moebius.backend.model.MoebiusPrincipal;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountService implements ReactiveUserDetailsService {

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

	public long getExpirationMillis(MultiValueMap<String, String> formData) {

		long expirationMillis = properties.getJwt().getExpirationMillis();
		String expirationMillisStr = formData.getFirst("expirationMillis");
		if (StringUtils.isNotBlank(expirationMillisStr))
			expirationMillis = Long.parseLong(expirationMillisStr);

		return expirationMillis;
	}
}
