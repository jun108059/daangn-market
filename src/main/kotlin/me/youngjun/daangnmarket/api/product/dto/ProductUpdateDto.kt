package me.youngjun.daangnmarket.api.product.dto

import me.youngjun.daangnmarket.common.domain.ProductStatus

data class ProductUpdateDto(
    val id: Long,
    val title: String,
    val categoryId: Long,
    val price: Long,
    val content: String,
    val imageList: List<String>,
    val status: ProductStatus? = ProductStatus.TRADING
)
