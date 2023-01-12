package me.youngjun.daangnmarket.infra.jwt

import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.repository.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val memberRepository: MemberRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val member: Member = memberRepository.findByEmail(username)
            ?: throw NullPointerException("회원 정보가 존재하지 않습니다.")

        return UserDetailsImpl(member)
    }
}