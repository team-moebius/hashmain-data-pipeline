package com.moebius.backend.api;

import com.moebius.backend.dto.LoginDto;
import com.moebius.backend.dto.MemberDto;
import com.moebius.backend.dto.SignupDto;
import com.moebius.backend.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final ModelMapper modelMapper;

	@ApiOperation("로그인, 성공할 경우 Bearer token이 body에 담겨져 전달된다.")
	@PostMapping("/member")
	public Mono<ResponseEntity<?>> login(@RequestBody @Valid LoginDto loginDto) {
		return memberService.login(loginDto);
	}

	@ApiOperation("회원가입")
	@PostMapping("/member/signup")
	public Mono<ResponseEntity<?>> signup(@RequestBody @Valid SignupDto signupDto) {
		return memberService.createAccount();
	}

	@ApiOperation("비밀번호 초기화")
	@PostMapping("/member/password")
	public Mono<ResponseEntity<?>> findPassword(@RequestBody @ApiParam(value = "초기화된 비밀번호를 전송할 이메일", required = true) String email) {
		return null;
	}

	@ApiOperation("이메일 인증 요청")
	@PostMapping("/member/email")
	public Mono<ResponseEntity<?>> requestToVerifyEmail(@RequestBody @ApiParam(value = "인증할 이메일", required = true) String email) {
		return null;
	}

	@ApiOperation("이메일 인증 확인")
	@PostMapping("/member/email/verification")
	public Mono<ResponseEntity<?>> verifyEmail(@RequestBody @ApiParam(value = "인증 확인용 코드, 이메일 인증 요청시 해당 이메일로 발송된다.", required = true) String authCode) {
		return null;
	}

	@ApiOperation("사용자 정보 조회")
	@GetMapping("/members/{id}")
	@PreAuthorize("hasAuthority('MEMBER')")
	public Mono<ResponseEntity<MemberDto>> getMember(@PathVariable String id) {
		return null;
	}


}
