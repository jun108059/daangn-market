package me.youngjun.daangnmarket.infra.domain.mapping

import me.youngjun.daangnmarket.infra.domain.Member
import me.youngjun.daangnmarket.member.dto.MemberJoinForm
import org.mapstruct.Mapper

@Mapper
interface MemberConverter {
    fun convertToEntity(memberJoinForm: MemberJoinForm): Member
}