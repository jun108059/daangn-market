package me.youngjun.daangnmarket.api.product.mapper

import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.common.domain.Product

class ProductViewMapper {
    companion object {
        fun convertToProductView(
            product: Product,
            likeCount: Int,
            chatCount: Int,
            imageUrl: String
        ): ProductView {
            return ProductView(
                id = product.id!!,
                areaName = product.area!!.areaName,
                title = product.title,
                price = product.price,
                likeCount = likeCount,
                chatCount = chatCount,
                productStatus = product.status!!.value,
                imgUrl = imageUrl
            )

        }
    }
}