package me.youngjun.daangnmarket.member.dto

data class MemberJoinForm(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var phone: String = "",
    var nickname: String = "",
    var area: String = "",
)