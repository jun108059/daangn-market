package me.youngjun.daangnmarket.infra.jwt

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @Value("\${jwt.secretKey}") private val secretKey: String,
    @Value("\${jwt.accessValidTime}") private val expiredTime: Long // 30분
) {

    companion object {
        const val AUTHORITIES_KEY = "auth"
        const val BEARER_TYPE = "Bearer"
        private val log = KotlinLogging.logger {}
    }

    private final val keyBytes: ByteArray = Decoders.BASE64.decode(secretKey)
    val key: SecretKey = Keys.hmacShaKeyFor(keyBytes)

    fun generateTokenDto(
        authentication: Authentication
    ): TokenDto {
        println("generate Token DTO authentication = $authentication")
        // 권한들 가져오기
        val authorities: String = authentication.authorities.joinToString {
            it.authority
        }
        println("authorities = $authorities")
        val now = Date().time

        // Access Token 생성
        val accessTokenExpiresIn = Date(now + expiredTime)
        val accessToken = Jwts.builder()
            .setSubject(authentication.name) // payload "sub": "name"
            .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
            .setExpiration(accessTokenExpiresIn) // payload "exp": 1516239022 (예시)
            .signWith(key, SignatureAlgorithm.HS512) // header "alg": "HS512"
            .compact()

        println("accessToken = $accessToken")
        return TokenDto(
            grantType = BEARER_TYPE,
            accessToken = accessToken,
            accessTokenExpiresIn = accessTokenExpiresIn.time
        )
    }

    fun getAuthentication(accessToken: String): Authentication? {
        // 토큰 복호화
        val claims = parseClaims(accessToken)
        if (claims[AUTHORITIES_KEY] == null) {
            throw RuntimeException("권한 정보가 없는 토큰입니다.")
        }

        // 클레임에서 권한 정보 가져오기
        val authorities = claims[AUTHORITIES_KEY].toString().split(",")
            .map { role: String? -> SimpleGrantedAuthority(role) }
            .toList()

        // UserDetails 객체를 만들어서 Authentication 리턴
        val principal: UserDetails = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun validateToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
            log.info { "잘못된 JWT 서명입니다." }
        } catch (e: MalformedJwtException) {
            log.info { "잘못된 JWT 서명입니다." }
        } catch (e: ExpiredJwtException) {
            log.info { "만료된 JWT 토큰입니다." }
        } catch (e: UnsupportedJwtException) {
            log.info { "지원되지 않는 JWT 토큰입니다." }
        } catch (e: IllegalArgumentException) {
            log.info { "JWT 토큰이 잘못되었습니다." }
        }
        return false
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}
