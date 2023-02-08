package me.youngjun.daangnmarket.api.product.dto

import me.youngjun.daangnmarket.common.domain.ProductStatus

data class ProductFilterDto(
    val categoryId: Long?,
    val status: ProductStatus?,
    val likes: Boolean?,
    val memberId: Long?,
    val searchKeyWord: String?
)
