package me.youngjun.daangnmarket.api.member.dto

data class LoginRequestDto(
    var email: String = "",
    var password: String = "",
)