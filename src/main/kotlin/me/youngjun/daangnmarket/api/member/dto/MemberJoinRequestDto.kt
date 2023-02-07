package me.youngjun.daangnmarket.api.member.dto

import me.youngjun.daangnmarket.common.domain.enum.Role

data class MemberJoinRequestDto(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var phone: String = "",
    var nickname: String = "",
    var areaId: Long = 1,
    var role: Role? = Role.ROLE_USER
)
