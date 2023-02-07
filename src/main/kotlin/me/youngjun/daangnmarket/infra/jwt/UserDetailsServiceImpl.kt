package me.youngjun.daangnmarket.infra.jwt

import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.repository.MemberRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(
        username: String
    ): UserDetails? {
        val member = memberRepository.findByEmail(username)
            ?: throw UsernameNotFoundException("[$username] -> 데이터베이스에서 찾을 수 없습니다.")
        return createUserDetails(member)
    }

    // DB 에 User 값이 존재한다면 UserDetails 객체로 만들어서 리턴
    private fun createUserDetails(
        member: Member
    ): UserDetails {
        val simpleGrantedAuthority: GrantedAuthority = SimpleGrantedAuthority("ROLE_USER")
        val grantedAuthority = listOf(simpleGrantedAuthority)

        return User(
            member.id.toString(),
            member.password,
            grantedAuthority
        )
    }
}
