package com.moebius.api.controller;

import com.moebius.api.dto.UserDto;
import com.moebius.backend.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/v1/account")
public class AccountController {
	private final AccountService accountService;
	private final ModelMapper modelMapper;

	public AccountController(AccountService accountService, ModelMapper modelMapper) {
		this.accountService = accountService;
		this.modelMapper = modelMapper;
	}

	@GetMapping("/{name}")
	public Mono<UserDto> findUser(@PathVariable String name) {
		return accountService.findByName(name)
			.map(user -> modelMapper.map(user, UserDto.class));
	}
}
