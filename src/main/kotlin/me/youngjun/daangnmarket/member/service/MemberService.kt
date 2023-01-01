package me.youngjun.daangnmarket.member.service

import me.youngjun.daangnmarket.infra.domain.Member
import me.youngjun.daangnmarket.infra.domain.mapping.MemberConverter
import me.youngjun.daangnmarket.infra.exception.DuplicationMemberException
import me.youngjun.daangnmarket.infra.exception.ErrorCode
import me.youngjun.daangnmarket.member.dto.LoginForm
import mu.KotlinLogging
import me.youngjun.daangnmarket.member.dto.MemberJoinForm
import me.youngjun.daangnmarket.member.repository.MemberRepository
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
        form: MemberJoinForm
    ): Member {
        val converter = Mappers.getMapper(MemberConverter::class.java)
        checkDuplicateUser(form.email)
        val member = converter.convertToEntity(form)
        val savedMember = memberRepository.save(member)
        log.info { "Join completed : ${savedMember.id}" }
        return savedMember
    }

    fun login(
        form: LoginForm
    ) {
        // 이메일/PW 일치 여부 검사 로직
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
}