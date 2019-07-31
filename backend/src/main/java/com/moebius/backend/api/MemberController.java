package com.moebius.backend.api;

import com.moebius.backend.dto.frontend.LoginDto;
import com.moebius.backend.dto.frontend.SignupDto;
import com.moebius.backend.dto.frontend.VerificationDto;
import com.moebius.backend.service.member.EmailService;
import com.moebius.backend.service.member.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;
	private final EmailService emailService;

	@ApiOperation(
		value = "로그인",
		httpMethod = "POST",
		response = String.class,
		notes = "성공할 경우 Json web token이 body에 담겨져 전달된다. 권한이 필요한 모든 요청의 Header에 'Authorization:Bearer ${JSON_WEB_TOKEN}'의 형태로 발송하면 된다."
	)
	@PostMapping("")
	public Mono<ResponseEntity<String>> login(@RequestBody @Valid @ApiParam(value = "로그인 시 필요한 정보", required = true) LoginDto loginDto) {
		return memberService.login(loginDto);
	}

	@ApiOperation(
		value = "회원가입",
		httpMethod = "POST",
		response = String.class
	)
	@PostMapping("/signup")
	public Mono<ResponseEntity<?>> signup(@RequestBody @Valid @ApiParam(value = "회원가입 시 필요한 정보", required = true) SignupDto signupDto) {
		return memberService.createAccount(signupDto);
	}

	@ApiOperation(
		value = "비밀번호 초기화",
		httpMethod = "POST",
		response = String.class
	)

	@PostMapping("/password")
	public Mono<ResponseEntity<?>> findPassword(@RequestBody @ApiParam(value = "초기화된 비밀번호를 전송할 이메일", required = true) String email) {
		return null;
	}

	@ApiOperation("이메일 인증 요청")
	@PostMapping("/{email}")
	public Mono<ResponseEntity<?>> requestToVerifyEmail(@PathVariable @ApiParam(value = "인증할 이메일", required = true) String email) {
		return emailService.requestToVerifyEmail(email);
	}

	@ApiOperation("중복된 이메일 여부 조회")
	@GetMapping("/duplicated-email/{email}")
	public Mono<ResponseEntity<Boolean>> getMember(@PathVariable String email) {
		return memberService.isDuplicatedMember(email);
	}

	@GetMapping("/verification")
	public Mono<ResponseEntity<?>> verifyEmail(@ModelAttribute @Valid VerificationDto verificationDto) {
		return emailService.verifyEmail(verificationDto);
	}
}
