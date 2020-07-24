package com.moebius.backend.assembler

import com.moebius.backend.domain.members.Level
import com.moebius.backend.domain.members.Member
import com.moebius.backend.dto.frontend.MemberDto
import com.moebius.backend.dto.frontend.SignupDto
import org.bson.types.ObjectId
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Subject

class MemberAssemblerTest extends Specification {
	def passwordEncoder = Mock(PasswordEncoder)

	@Subject
	def memberAssembler = new MemberAssembler(passwordEncoder)

	def "Should assemble member"() {
		given:
		def signUpDto = new SignupDto()
		signUpDto.setName("test")
		signUpDto.setEmail("test@hashmainpro.com")
		signUpDto.setPassword("test!3@4")

		when:
		def result = memberAssembler.assembleMember(signUpDto)

		then:
		1 * passwordEncoder.encode(_ as CharSequence)

		result instanceof Member
		result.getName() == "test"
		result.getEmail() == "test@hashmainpro.com"
	}

	def "Should assemble dto"() {
		given:
		def member = new Member()
		member.setId(new ObjectId("5d8620bf46e0fb0001d64265"))
		member.setName("test")
		member.setEmail("test@hashmainpro.com")
		member.setPassword("test!3@4")

		when:
		def result = memberAssembler.assembleDto(member)

		then:
		result instanceof MemberDto
		result.getLevel() == Level.NORMAL
		result.getName() == "test"
		result.getEmail() == "test@hashmainpro.com"
	}
}
