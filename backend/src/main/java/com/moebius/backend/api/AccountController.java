package com.moebius.backend.api;

import com.moebius.backend.dto.AccountResponseDto;
import com.moebius.backend.dto.MemberDto;
import com.moebius.backend.model.MoebiusPrincipal;
import com.moebius.backend.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@CrossOrigin
@RestController
@RequestMapping("/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final ModelMapper modelMapper;

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
                            accountService.addAuthHeader(serverWebExchange.getResponse(), tuple.getT1());
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
