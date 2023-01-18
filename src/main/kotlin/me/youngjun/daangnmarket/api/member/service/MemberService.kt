package me.youngjun.daangnmarket.api.member.service

import me.youngjun.daangnmarket.api.member.dto.MemberInfoResponseDto
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.api.member.dto.ProfileChangeRequestDto
import me.youngjun.daangnmarket.common.domain.Area
import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.domain.enum.Role
import me.youngjun.daangnmarket.common.repository.AreaRepository
import me.youngjun.daangnmarket.common.repository.MemberRepository
import me.youngjun.daangnmarket.infra.exception.DuplicationMemberException
import me.youngjun.daangnmarket.infra.exception.ErrorCode
import me.youngjun.daangnmarket.infra.exception.NotFoundAreaException
import me.youngjun.daangnmarket.infra.exception.NotFoundMemberException
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val areaRepository: AreaRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    companion object {
        private val log = KotlinLogging.logger {}
    }

    @Transactional
    fun join(
        form: MemberJoinRequestDto
    ): Long {
        val area = areaRepository.findByIdOrNull(form.areaId)
            ?: NotFoundAreaException(ErrorCode.AREA_NOT_FOUND)
        val member = Member.convertToEntity(form)
        member.areaId = area as Area
        // 이메일 중복 검사
        checkDuplicateUser(form.email)
        // 비밀번호 암호화
        member.bcryptPassword(passwordEncoder.encode(form.password))
        member.role = Role.ROLE_USER

        val savedMember = memberRepository.save(member)
        log.info { "Join completed : ${savedMember.id}" }
        return savedMember.id ?: 0
    }

    fun getMemberInfo(
        memberId: Long
    ): MemberInfoResponseDto {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw NotFoundMemberException(ErrorCode.DEFAULT_NOT_FOUND)
        return MemberInfoResponseDto(
            nickname = member.nickname,
            imagePath = member.imagePath
        )
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