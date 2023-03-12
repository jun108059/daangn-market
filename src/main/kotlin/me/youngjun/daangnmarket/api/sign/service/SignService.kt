package me.youngjun.daangnmarket.api.sign.service

import me.youngjun.daangnmarket.api.sign.dto.LoginRequestDto
import me.youngjun.daangnmarket.api.sign.dto.LoginResponseDto
import me.youngjun.daangnmarket.common.repository.MemberRepository
import me.youngjun.daangnmarket.infra.jwt.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SignService(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository,
) {
    @Transactional(readOnly = true)
    fun loginByEmail(
        dto: LoginRequestDto,
    ): LoginResponseDto {
        // email, password 기반 AuthenticationToken 생성
        val authenticationToken = UsernamePasswordAuthenticationToken(dto.email, dto.password)
        // 검증 (사용자 비밀버호 체크) - 내부에서 UserDetailsServiceImpl.loadUserByUsername 호출
        val authenticate = authenticationManager.authenticate(authenticationToken)
        // 인증 정보 기반 JWT 토큰 생성
        val tokenDto = jwtTokenProvider.generateTokenDto(authenticate)
        val member = memberRepository.findByEmail(dto.email)
            ?: throw UsernameNotFoundException("[$dto.email] -> 데이터베이스에서 찾을 수 없습니다.")
        return LoginResponseDto(
            id = member.id,
            name = member.name,
            grantType = tokenDto.grantType,
            accessToken = tokenDto.accessToken,
            accessTokenExpiresIn = tokenDto.accessTokenExpiresIn,
        )
    }
}
