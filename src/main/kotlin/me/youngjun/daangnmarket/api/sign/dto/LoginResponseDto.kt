package me.youngjun.daangnmarket.api.sign.dto

data class LoginResponseDto(
    val id: Long?,
    val name: String,
    val grantType: String,
    val accessToken: String,
    val accessTokenExpiresIn: Long,
)
