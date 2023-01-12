package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun existsByEmail(email: String): Boolean
    fun findByEmailAndPassword(email: String, password: String): Member
    fun findByEmail(email: String): Member?
}