package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByCode(code: String): Category
}
