package com.moebius.api.controller;

import com.moebius.api.dto.AccountResponseDto;
import com.moebius.api.dto.UserDto;
import com.moebius.backend.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;
	private final ModelMapper modelMapper;

	@GetMapping("/")
	public Mono<UserDto> findUser(Mono<Principal> principalMono) {
		return principalMono.flatMap(principal -> accountService.findByName(principal.getName()))
			.map(user -> modelMapper.map(user, UserDto.class));
	}

	@PostMapping("/")
	public Mono<UserDto> login(ServerWebExchange serverWebExchange) {
		return ReactiveSecurityContextHolder.getContext()
			.map(SecurityContext::getAuthentication)
			.map(Authentication::getPrincipal)
			.cast(UserDto.class); // TODO : Need to erase credentials and create session by serverWebExchange.
	}

	@PostMapping("/signup")
	@PreAuthorize("!hasAuthority('USER')")
	public Mono<AccountResponseDto> createUser(@RequestBody UserDto userDto) {
		return accountService.createAccount()
			.map(AccountResponseDto::new);
	}
}
