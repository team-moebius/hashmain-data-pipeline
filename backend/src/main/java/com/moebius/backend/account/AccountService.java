package com.moebius.backend.account;

import com.moebius.backend.database.users.User;
import com.moebius.backend.database.users.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountService implements ReactiveUserDetailsService {

	private final UserRepository userRepository;

	public AccountService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		Mono<User> user = userRepository.findByName(username);

		return user.cast(UserDetails.class);
	}
}
