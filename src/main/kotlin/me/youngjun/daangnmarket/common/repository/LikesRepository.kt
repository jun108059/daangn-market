package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Likes
import org.springframework.data.jpa.repository.JpaRepository

interface LikesRepository : JpaRepository<Likes, Long> {
    fun existsByMemberIdAndProductId(memberId: Long, productId: Long): Boolean
}