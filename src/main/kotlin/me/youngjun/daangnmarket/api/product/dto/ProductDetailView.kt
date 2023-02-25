package me.youngjun.daangnmarket.api.product.dto

import com.fasterxml.jackson.annotation.JsonFormat
import me.youngjun.daangnmarket.common.domain.ProductStatus
import java.time.LocalDateTime

data class ProductDetailView(
    val id: Long,
    val memberId: Long,
    val areaName: String,
    val title: String,
    val price: Long,
    val likesCount: Int,
    val chatCount: Int,
    val status: ProductStatus,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val createdDateTime: LocalDateTime,
    val imageUrls: List<String>,
    val nickname: String,
    val category: String,
    val content: String,
    val sellerImageUrl: String?,
    var isLikes: Boolean?,
    var isMine: Boolean?,
)
