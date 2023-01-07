package me.youngjun.daangnmarket.api.member.dto

data class MemberJoinRequestDto(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var phone: String = "",
    var nickname: String = "",
    var area: String = "",
)