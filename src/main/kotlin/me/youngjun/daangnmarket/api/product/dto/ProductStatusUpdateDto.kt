package me.youngjun.daangnmarket.api.product.dto

import me.youngjun.daangnmarket.common.domain.ProductStatus

data class ProductStatusUpdateDto(
    val id: Long,
    val status: ProductStatus,
)
