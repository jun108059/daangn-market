package me.youngjun.daangnmarket.common.domain.mapping

import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import org.mapstruct.Mapper

@Mapper
interface MemberConverter {
    fun convertToEntity(memberJoinRequestDto: MemberJoinRequestDto): Member
}