package com.moebius.backend.service.member

import com.moebius.backend.assembler.MemberAssembler
import com.moebius.backend.domain.members.Member
import com.moebius.backend.domain.members.MemberRepository
import com.moebius.backend.dto.frontend.LoginDto
import com.moebius.backend.dto.frontend.MemberDto
import com.moebius.backend.dto.frontend.SignupDto
import com.moebius.backend.exception.DataNotFoundException
import com.moebius.backend.exception.DataNotVerifiedException
import com.moebius.backend.exception.DuplicatedDataException
import com.moebius.backend.exception.WrongDataException
import com.mongodb.DuplicateKeyException
import com.mongodb.ServerAddress
import com.mongodb.WriteConcernResult
import org.bson.BsonDocument
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
				.verifyComplete()
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
				.verifyComplete()
	}

	def "Should create member"() {
		given:
		1 * memberAssembler.toMember(_ as SignupDto) >> Stub(Member)
		1 * memberRepository.save(_ as Member) >> Mono.just(Stub(Member))
		1 * emailService.requestToVerifyEmail(_ as String) >> Mono.just(ResponseEntity.ok(HttpStatus.OK.getReasonPhrase()))

		expect:
		StepVerifier.create(memberService.createMember(Stub(SignupDto)))
				.assertNext({
					it != null
					it.getStatusCode() == HttpStatus.OK
				})
				.verifyComplete()
	}

	def "Should not create duplicated member"() {
		given:
		1 * memberAssembler.toMember(_ as SignupDto) >> Stub(Member)
		1 * memberRepository.save(_ as Member) >> { throw(new DuplicateKeyException(Stub(BsonDocument), Stub(ServerAddress), Stub(WriteConcernResult))) }

		expect:
		StepVerifier.create(memberService.createMember(Stub(SignupDto)))
				.verifyError(DuplicatedDataException.class)
	}

	def "Should login"() {
		given:
		def member = Stub(Member) {
			isActive() >> true
		}

		1 * memberRepository.findByEmail(_ as String) >> Mono.just(member)
		1 * passwordEncoder.matches(_ as CharSequence, _ as String) >> true

		expect:
		StepVerifier.create(memberService.login(Stub(LoginDto)))
				.assertNext({
					it != null
					it.getStatusCode() == HttpStatus.OK
				})
				.verifyComplete()
	}

	def "Should not login because of not existent email"() {
		given:
		1 * memberRepository.findByEmail(_ as String) >> Mono.empty()

		expect:
		StepVerifier.create(memberService.login(Stub(LoginDto)))
				.verifyError(DataNotFoundException.class)
	}

	def "Should not login because of wrong password"() {
		given:
		1 * memberRepository.findByEmail(_ as String) >> Mono.just(Stub(Member))
		1 * passwordEncoder.matches(_ as CharSequence, _ as String) >> false

		expect:
		StepVerifier.create(memberService.login(Stub(LoginDto)))
				.verifyError(WrongDataException.class)
	}

	def "Should not login because of unactivated member"() {
		given:
		def member = Stub(Member) {
			isActive() >> false
		}

		1 * memberRepository.findByEmail(_ as String) >> Mono.just(member)
		1 * passwordEncoder.matches(_ as CharSequence, _ as String) >> true

		expect:
		StepVerifier.create(memberService.login(Stub(LoginDto)))
				.verifyError(DataNotVerifiedException.class)
	}

	def "Should get member"() {
		given:
		1 * memberRepository.findById(_ as ObjectId) >> Mono.just(Stub(Member))
		1 * memberAssembler.toDto(_ as Member) >> Stub(MemberDto)

		expect:
		StepVerifier.create(memberService.getMember("5e52ab07b6e75567ac2376ae"))
				.assertNext({
					it != null
					it.getStatusCode() == HttpStatus.OK
				})
				.verifyComplete()
	}
}
