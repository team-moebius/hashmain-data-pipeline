package com.moebius.backend.api;

import com.moebius.backend.dto.AccountResponseDto;
import com.moebius.backend.dto.MemberDto;
import com.moebius.backend.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final ModelMapper modelMapper;

    @PostMapping("/")
    public Mono<ResponseEntity<?>> login(@RequestBody MemberDto memberDto) {
        return memberService.login(memberDto);

    }

    @PostMapping("/signup")
    public Mono<AccountResponseDto> signup(@RequestBody MemberDto memberDto) {
        return memberService.createAccount()
                .map(AccountResponseDto::new);
    }

    @PostMapping("/password")
    public Mono<AccountResponseDto> findPassword(@RequestBody MemberDto memberDto) {
        return null;
    }

    @PostMapping("/verification")
    public Mono<AccountResponseDto> verifyMember(@RequestBody MemberDto memberDto) {
        return null;
    }


}
