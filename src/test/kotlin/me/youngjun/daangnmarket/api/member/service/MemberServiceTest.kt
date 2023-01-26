package me.youngjun.daangnmarket.api.member.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.common.domain.Area
import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.repository.AreaRepository
import me.youngjun.daangnmarket.common.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

internal class MemberServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val areaRepository = mockk<AreaRepository>()
    val passwordEncoder = mockk<BCryptPasswordEncoder>()
    val target = MemberService(memberRepository, areaRepository, passwordEncoder)

    Given("회원 가입 데이터") {
        every { areaRepository.findByIdOrNull(any()) } returns Area.of("0001")
        every { memberRepository.existsByEmail(any()) } returns false
        every { memberRepository.save(savedMember) } returns savedMember
        every { passwordEncoder.encode(any()) } returns "1234Encoding"

        When("회원 가입을 요청하면") {
            withContext(Dispatchers.IO) {
                target.join(memberJoinRequestDto)
            }
            Then("이메일 중복 검사를 1번 해야한다") {
                verify(exactly = 1) {
                    target.checkDuplicateUser(any())
                }
            }
            Then("회원 정보가 저장되어야 한다") {
                verify(exactly = 1) {
                    memberRepository.save(any())
                }
            }
        }
        When("이미 가입된 회원이 요청하면") {
            every { memberRepository.existsByEmail(any()) } returns true
            Then("DuplicateException 예외를 던진다") {
                shouldThrow<DuplicateException> {
                    target.join(memberJoinRequestDto)
                }
            }
        }
    }

    Given("회원 조회") {
        val memberId = 1L
        every { memberRepository.findByIdOrNull(memberId) } returns savedMember

        When("회원 데이터가 존재한다면") {
            val result = target.getMemberInfo(memberId)
            Then("DB 에서 데이터를 조회한다") {
                verify(exactly = 1) {
                    memberRepository.findByIdOrNull(memberId)
                }
            }
            Then("응답 데이터가 반환된다") {
                result.imagePath shouldBe savedMember.imagePath
                result.nickname shouldBe savedMember.nickname
            }
        }

    }

}) {
    companion object {
        private val memberJoinRequestDto = MemberJoinRequestDto(
            email = "youngjun@test.com",
            password = "1234",
            name = "박영준",
            phone = "010-1234-1234",
            nickname = "개발하는만두",
            areaId = 1
        )

        val savedMember = Member(
            email = "youngjun@test.com",
            password = "1234Encoding",
            name = "박영준",
            phone = "010-1234-1234",
            imagePath = "",
            nickname = "개발하는만두",
            areaId = Area.of("0001")
        )

    }

}