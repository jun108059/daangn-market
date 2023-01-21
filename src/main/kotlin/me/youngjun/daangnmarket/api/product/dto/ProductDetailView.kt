package me.youngjun.daangnmarket.api.product.dto

import java.time.LocalDateTime

data class ProductDetailView(
    val imagePaths: List<String>,
    val imagePathCount: Int,
    val nickname: String,
    val title: String,
    val price: Long,
    val category: String,
    val createAt: LocalDateTime,
    val content: String,
    val chatCount: Int,
    val likesCount: Int
)
