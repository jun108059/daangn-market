package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Product
import me.youngjun.daangnmarket.common.domain.Room
import org.springframework.data.jpa.repository.JpaRepository

interface RoomRepository : JpaRepository<Room, Long> {
    fun findByProductId(productId: Product): List<Room>
}
