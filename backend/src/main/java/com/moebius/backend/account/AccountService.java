package com.moebius.backend.account;

import com.moebius.backend.database.users.User;
import com.moebius.backend.database.users.UserRepository;
import com.moebius.backend.model.AccountResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountService {

	private final UserRepository userRepository;

	public AccountService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Mono<User> findByName(String name) {
		return userRepository.findByName(name);
	}

	public Mono<AccountResponse> createAccount() {
		return Mono.just(new AccountResponse());
	}
}
