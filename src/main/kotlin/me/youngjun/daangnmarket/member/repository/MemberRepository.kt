package me.youngjun.daangnmarket.member.repository

import me.youngjun.daangnmarket.infra.domain.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

}