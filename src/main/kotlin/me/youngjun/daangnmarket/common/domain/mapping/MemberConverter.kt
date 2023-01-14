package me.youngjun.daangnmarket.common.domain.mapping

import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.common.domain.Member
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper
interface MemberConverter {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    fun convertToEntity(memberJoinRequestDto: MemberJoinRequestDto): Member
}