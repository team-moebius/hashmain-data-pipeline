package com.moebius.api.controller;

import com.moebius.api.dto.AccountResponseDto;
import com.moebius.api.dto.MemberDto;
import com.moebius.backend.account.AccountService;
import com.moebius.backend.security.MoebiusPrincipal;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
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
    public Mono<MemberDto> findUser(Mono<Principal> principalMono) {
        return principalMono.flatMap(principal -> accountService.findByName(principal.getName()))
                .map(user -> modelMapper.map(user, MemberDto.class));
    }

    @PostMapping("/")
    public Mono<MemberDto> login(ServerWebExchange serverWebExchange) {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(MoebiusPrincipal.class)
                .doOnNext(MoebiusPrincipal::eraseCredentials)
                .map(MoebiusPrincipal::currentMember)
                .zipWith(serverWebExchange.getFormData()).
                        doOnNext(tuple -> {

                            // TODO : Need to implement service for adding auth header
                        })
                // TODO : Need to check this process well (Member -> MemberDto)
                .map(tuple -> modelMapper.map(tuple.getT1(), MemberDto.class));

    }

    @PostMapping("/signup")
    @PreAuthorize("!hasAuthority('USER')")
    public Mono<AccountResponseDto> signup(@RequestBody MemberDto memberDto) {
        return accountService.createAccount()
                .map(AccountResponseDto::new);
    }
}
