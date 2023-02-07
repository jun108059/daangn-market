package me.youngjun.daangnmarket.api.sign.service

import me.youngjun.daangnmarket.api.sign.dto.LoginRequestDto
import me.youngjun.daangnmarket.infra.jwt.JwtTokenProvider
import me.youngjun.daangnmarket.infra.jwt.TokenDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider
) {
    @Transactional(readOnly = true)
    fun loginByEmail(
        dto: LoginRequestDto
    ): TokenDto {
        // email, password 기반 AuthenticationToken 생성
        val authenticationToken = UsernamePasswordAuthenticationToken(dto.email, dto.password)
        println("authenticationToken = $authenticationToken")
        // 검증 (사용자 비밀버호 체크) - 내부에서 UserDetailsServiceImpl.loadUserByUsername 호출
        val authenticate = authenticationManager.authenticate(authenticationToken)
        println("authenticate = $authenticate")
        // 인증 정보 기반 JWT 토큰 생성
        return jwtTokenProvider.generateTokenDto(authenticate)
    }
}
