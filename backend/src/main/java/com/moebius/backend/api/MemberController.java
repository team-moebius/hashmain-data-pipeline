package com.moebius.backend.api;

import com.moebius.backend.dto.LoginDto;
import com.moebius.backend.dto.MemberDto;
import com.moebius.backend.dto.SignupDto;
import com.moebius.backend.dto.VerificationDto;
import com.moebius.backend.service.member.EmailService;
import com.moebius.backend.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final EmailService emailService;

	@ApiOperation(
		value = "로그인",
		httpMethod = "POST",
		notes = "성공할 경우 Json web token이 body에 담겨져 전달된다. 권한이 필요한 모든 요청의 Header에 'Authorization:Bearer ${JSON_WEB_TOKEN}'의 형태로 발송하면 된다."
	)
	@PostMapping("/member")
	public Mono<ResponseEntity<String>> login(@RequestBody @ApiParam(value = "로그인 시 필요한 정보", required = true) LoginDto loginDto) {
		return memberService.login(loginDto);
	}

	@ApiOperation("회원가입")
	@PostMapping("/member/signup")
	public Mono<ResponseEntity<?>> signup(@RequestBody @ApiParam(value = "회원가입 시 필요한 정보", required = true) SignupDto signupDto) {
		return memberService.createAccount(signupDto);
	}

	@ApiOperation("비밀번호 초기화")
	@PostMapping("/member/password")
	public Mono<ResponseEntity<?>> findPassword(@RequestBody @ApiParam(value = "초기화된 비밀번호를 전송할 이메일", required = true) String email) {
		return null;
	}

	@ApiOperation("이메일 인증 요청")
	@PostMapping("/member/email")
	public Mono<ResponseEntity<?>> requestToVerifyEmail(@RequestBody @ApiParam(value = "인증할 이메일", required = true) String email) {
		return emailService.requestToVerifyEmail(email);
	}

	@ApiOperation("이메일 인증 확인")
	@PostMapping("/member/email/verification")
	public Mono<ResponseEntity<?>> verifyEmail(@ModelAttribute VerificationDto verificationDto) {
		return emailService.verifyEmail(verificationDto);
	}

	@ApiOperation("사용자 정보 조회")
	@GetMapping("/members/{email}")
	@PreAuthorize("hasAuthority('MEMBER')")
	public Mono<ResponseEntity<MemberDto>> getMember(@PathVariable String id) {
		return null;
	}

}
