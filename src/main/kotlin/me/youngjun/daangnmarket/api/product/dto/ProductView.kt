package me.youngjun.daangnmarket.api.product.dto

import com.fasterxml.jackson.annotation.JsonFormat
import me.youngjun.daangnmarket.common.domain.ProductStatus
import java.time.LocalDateTime

data class ProductView(
    val id: Long,
    val areaName: String,
    val title: String,
    val price: Long,
    val likeCount: Int,
    val chatCount: Int,
    val status: ProductStatus,
    val imgUrl: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val createdDateTime: LocalDateTime,
)
