package me.youngjun.daangnmarket.api.product.dto

import me.youngjun.daangnmarket.common.domain.enum.ProductStatus

data class ProductView(
    val id: Long,
    val areaName: String,
    val title: String,
    val price: Int,
    val likeCount: Int,
    val chatCount: Int,
    val productStatus: ProductStatus,
    val imgUrl: String,
)
