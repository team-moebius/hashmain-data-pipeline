package com.moebius.backend.service.member;

import com.moebius.backend.domain.members.Member;
import com.moebius.backend.domain.members.MemberRepository;
import com.moebius.backend.dto.VerificationDto;
import com.moebius.backend.exception.EmailNotFoundException;
import com.moebius.backend.exception.ExceptionTypes;
import com.moebius.backend.exception.VerificationFailedException;
import com.moebius.backend.utils.Verifier;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static com.moebius.backend.utils.ThreadScheduler.COMPUTE;
import static com.moebius.backend.utils.ThreadScheduler.IO;

@Service
@RequiredArgsConstructor
public class EmailService {
	private final JavaMailSender emailSender;
	private final MemberRepository memberRepository;
	private final TemplateEngine templateEngine;
	@Value("${moebius.protocol}")
	private String protocol;
	@Value("${moebius.host}")
	private String host;

	public Mono<ResponseEntity<?>> requestToVerifyEmail(@NonNull String email) {
		Hooks.onOperatorDebug();

		return memberRepository.findByEmail(email)
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.log()
			.switchIfEmpty(Mono.defer(() -> Mono.error(new EmailNotFoundException(ExceptionTypes.NONEXISTENT_DATA.getMessage(email)))))
			.filter(member -> !member.isActive())
			.switchIfEmpty(Mono.defer(() -> Mono.error(new VerificationFailedException(ExceptionTypes.ALREADY_VERIFIED_DATA.getMessage(email)))))
			.flatMap(this::updateVerificationCode)
			.flatMap(this::sendVerificationEmail);
	}

	public Mono<ResponseEntity<?>> verifyEmail(@NonNull VerificationDto verificationDto) {
		Verifier.checkNullField(verificationDto);
		Hooks.onOperatorDebug();

		return memberRepository.findByEmail(verificationDto.getEmail())
			.subscribeOn(IO.scheduler())
			.publishOn(COMPUTE.scheduler())
			.log()
			.switchIfEmpty(Mono.defer(() -> Mono.error(new EmailNotFoundException(ExceptionTypes.NONEXISTENT_DATA.getMessage(verificationDto.getEmail())))))
			.filter(member -> !member.isActive() && member.getVerificationCode() != null)
			.switchIfEmpty(Mono.defer(() -> Mono.error(new VerificationFailedException(ExceptionTypes.ALREADY_VERIFIED_DATA.getMessage(verificationDto.getEmail())))))
			.filter(member -> member.getVerificationCode().equals(verificationDto.getCode()))
			.switchIfEmpty(Mono.defer(() -> Mono.error(new VerificationFailedException(ExceptionTypes.INVALID_CODE.getMessage()))))
			.flatMap(this::updateMember)
			.map(member -> ResponseEntity.ok(HttpStatus.OK.getReasonPhrase()));
	}

	private Mono<Member> updateVerificationCode(Member member) {
		return Mono.fromCallable(() -> {
			member.setVerificationCode(Verifier.generateCode());
			return member;
		}).subscribeOn(COMPUTE.scheduler())
			.publishOn(IO.scheduler())
			.flatMap(memberRepository::save);
	}

	/**
	 * FIXME : Should be refactored by extracting MimeMessagePreparator from EmailService.
	 * @param member
	 * @return
	 */
	private Mono<ResponseEntity<String>> sendVerificationEmail(Member member) {
		return Mono.fromCallable(() -> {
			MimeMessagePreparator messagePreparator = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, StandardCharsets.UTF_8.name());
				messageHelper.setFrom("cryptobox.master@gmail.com");
				messageHelper.setTo(member.getEmail());
				messageHelper.setSubject("크립토박스 인증 메일입니다.");

				Context context = new Context();
				context.setVariable("protocol", protocol);
				context.setVariable("host", host);
				context.setVariable("code", member.getVerificationCode());
				context.setVariable("name", member.getName());
				context.setVariable("email", member.getEmail());

				String content = templateEngine.process("verificationForm", context);
				messageHelper.setText(content, true);
			};
			try {
				emailSender.send(messagePreparator);
				return ResponseEntity.ok(HttpStatus.OK.getReasonPhrase());
			} catch (MailException me) {
				return ResponseEntity.badRequest().body(me.getMessage());
			}
		}).subscribeOn(COMPUTE.scheduler());
	}

	private Mono<Member> updateMember(Member member) {
		Hooks.onOperatorDebug();

		return Mono.fromCallable(() -> {
			member.setActive(true);
			member.setVerificationCode(null);
			member.setUpdatedAt(LocalDateTime.now());
			return member;
		}).subscribeOn(COMPUTE.scheduler())
			.publishOn(IO.scheduler())
			.log()
			.flatMap(memberRepository::save);
	}
}
