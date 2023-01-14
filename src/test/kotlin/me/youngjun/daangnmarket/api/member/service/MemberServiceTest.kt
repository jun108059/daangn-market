package me.youngjun.daangnmarket.api.member.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.repository.MemberRepository
import me.youngjun.daangnmarket.infra.exception.DuplicationMemberException
import me.youngjun.daangnmarket.infra.jwt.JwtTokenProvider
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@ExtendWith(MockKExtension::class)
internal class MemberServiceTest {

    @InjectMockKs
    private lateinit var target: MemberService

    @MockK
    private lateinit var memberRepository: MemberRepository

    @MockK
    private lateinit var authenticationManager: AuthenticationManager

    @MockK
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @MockK
    private lateinit var passwordEncoder: BCryptPasswordEncoder

    @DisplayName("회원가입 테스트")
    @Test
    fun memberJoinTest() {
        // given
        val memberJoinRequestDto = MemberJoinRequestDto(
            email = "youngjun@test.com",
            password = "1234",
            name = "박영준",
            phone = "010-1234-1234",
            nickname = "개발하는만두",
            area = "판교",
        )
        val savedMember = Member(
            id = 1,
            email = "youngjun@test.com",
            password = "1234Encoding",
            name = "박영준",
            phone = "010-1234-1234",
            nickname = "개발하는만두",
            area = "판교",
        )

        every {
            memberRepository.save(any())
        } returns savedMember

        every {
            memberRepository.existsByEmail(any())
        } returns false

        every {
            passwordEncoder.encode(any())
        } returns "1234Encoding"

        // when
        val result = target.join(memberJoinRequestDto)

        // then
        verify(exactly = 1) {
            memberRepository.save(any())
        }
        assert(result == 1.toLong())
    }

    @DisplayName("이메일 중복검사 테스트")
    @Test
    fun duplicateEmailTest() {
        // given
        val memberJoinRequestDto = MemberJoinRequestDto(
            email = "youngjun@test.com",
            password = "1234",
            name = "박영준",
            phone = "010-1234-1234",
            nickname = "개발하는만두",
            area = "판교",
        )
        val savedMember = Member(
            id = 1,
            email = "youngjun@test.com",
            password = "1234Encoding",
            name = "박영준",
            phone = "010-1234-1234",
            nickname = "개발하는만두",
            area = "판교",
        )

        every {
            memberRepository.save(any())
        } returns savedMember

        // when
        every {
            memberRepository.existsByEmail(any())
        } returns true

        // then
        assertThrows(DuplicationMemberException::class.java) {
            target.join(memberJoinRequestDto)
        }
    }
}