package me.youngjun.daangnmarket.api.member.service

import me.youngjun.daangnmarket.common.domain.mapping.MemberConverter
import me.youngjun.daangnmarket.infra.exception.DuplicationMemberException
import me.youngjun.daangnmarket.infra.exception.ErrorCode
import me.youngjun.daangnmarket.api.member.dto.LoginRequestDto
import mu.KotlinLogging
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.common.repository.MemberRepository
import org.mapstruct.factory.Mappers
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    companion object {
        private val log = KotlinLogging.logger {}
    }

    @Transactional
    fun join(
        form: MemberJoinRequestDto
    ): Long {
        val converter = Mappers.getMapper(MemberConverter::class.java)
        checkDuplicateUser(form.email)
        // TODO password 암호화
        val member = converter.convertToEntity(form)
        val savedMember = memberRepository.save(member)
        log.info { "Join completed : ${savedMember.id}" }
        return savedMember.id ?: 0
    }

    fun login(
        form: LoginRequestDto
    ) {
        // TODO 이메일/PW 일치 여부 검사 로직
        log.info { "Login completed : ${form.email}" }
    }

    fun checkDuplicateUser(
        email: String
    ) {
        val isExistence = memberRepository.existsByEmail(email)
        if (isExistence) {
            throw DuplicationMemberException(ErrorCode.DUPLICATE_USER_EMAIL)
        }
    }

    fun findMemberById(
        memberId: Long
    ) {
        val member = memberRepository.findById(memberId)
            ?: throw NullPointerException("회원 정보가 없습니다 id : ${memberId}")

    }
}