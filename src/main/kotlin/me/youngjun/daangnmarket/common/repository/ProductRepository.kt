package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Area
import me.youngjun.daangnmarket.common.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {
    fun findByAreaId(areaId: Area): List<Product>
}