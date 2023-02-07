package me.youngjun.daangnmarket.infra.jwt

data class TokenDto(
    val grantType: String,
    val accessToken: String,
    val accessTokenExpiresIn: Long
)
