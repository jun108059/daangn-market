package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Image
import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository : JpaRepository<Image, Long> {
    fun findByProductId(productId: Long): List<Image>
}