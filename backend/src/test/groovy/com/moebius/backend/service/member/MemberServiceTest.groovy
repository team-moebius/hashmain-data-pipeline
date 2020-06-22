package com.moebius.backend.service.member

import com.moebius.backend.assembler.MemberAssembler
import com.moebius.backend.domain.members.Member
import com.moebius.backend.domain.members.MemberRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification
import spock.lang.Subject

class MemberServiceTest extends Specification {
	def memberRepository = Mock(MemberRepository)
	def passwordEncoder = Mock(PasswordEncoder)
	def memberAssembler = Mock(MemberAssembler)
	def emailService = Mock(EmailService)
	def email = "somebody@dummy.com"

	@Subject
	def memberService = new MemberService(
			memberRepository,
			passwordEncoder,
			memberAssembler,
			emailService
	)

	def "Should check duplicated member"() {
		given:
		1 * memberRepository.findByEmail(email) >> Mono.just(Stub(Member))

		expect:
		StepVerifier.create(memberService.checkDuplicateMember(email))
				.assertNext({
					it != null
					it.getStatusCode() == HttpStatus.OK
					it.getBody() == email
				})
	}

	def "Should check non-duplicated member"() {
		given:
		1 * memberRepository.findByEmail(email) >> Mono.empty()

		expect:
		StepVerifier.create(memberService.checkDuplicateMember(email))
				.assertNext({
					it != null
					it.getStatusCode() == HttpStatus.OK
				})
	}

	def "Should create member"() {

	}

	def "Login"() {
	}

	def "Should get member"() {
	}
}
