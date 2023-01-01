package me.youngjun.daangnmarket.member.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.youngjun.daangnmarket.infra.domain.Member
import me.youngjun.daangnmarket.member.dto.MemberJoinForm
import me.youngjun.daangnmarket.member.repository.MemberRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class MemberServiceTest {

    @InjectMockKs
    private lateinit var target: MemberService

    @MockK
    private lateinit var memberRepository: MemberRepository

    @DisplayName("회원가입 테스트")
    @Test
    fun memberJoinTest() {
        // given
        val memberJoinForm = MemberJoinForm(
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
            password = "1234",
            name = "박영준",
            phone = "010-1234-1234",
            nickname = "개발하는만두",
            area = "판교",
        )

        every {
            memberRepository.save(any())
        } returns savedMember

        // when
        val result = target.join(memberJoinForm)

        // then
        verify(exactly = 1) {
            memberRepository.save(any())
        }
        assert(result.id == 1.toLong())
    }

}