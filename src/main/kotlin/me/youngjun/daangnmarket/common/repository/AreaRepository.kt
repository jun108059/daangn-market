package me.youngjun.daangnmarket.common.repository

import me.youngjun.daangnmarket.common.domain.Area
import org.springframework.data.jpa.repository.JpaRepository

interface AreaRepository : JpaRepository<Area, Long> {
    fun findByCode(code: String): Area
}
