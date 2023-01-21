package me.youngjun.daangnmarket.api.product.mapper

import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.common.domain.Product
import me.youngjun.daangnmarket.common.domain.enum.AreaEnum

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
                areaName = AreaEnum.getName(product.areaId.code),
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