package me.youngjun.daangnmarket.api.product.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ProductDetailView(
    val imagePaths: List<String>,
    val imagePathCount: Int,
    val nickname: String,
    val title: String,
    val price: Long,
    val category: String,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createAt: LocalDateTime,
    val content: String,
    val chatCount: Int,
    val likesCount: Int
)
