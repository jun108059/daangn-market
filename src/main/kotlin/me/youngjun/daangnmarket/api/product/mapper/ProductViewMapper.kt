package me.youngjun.daangnmarket.api.product.mapper

import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.common.domain.Product
import me.youngjun.daangnmarket.common.domain.enum.AreaEnum

class ProductViewMapper {
    companion object {
        private fun convertToProductView(
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

        fun convertViewList(
            productList: List<Product>
        ): MutableList<ProductView> {
            val productViewList = mutableListOf<ProductView>()
            for (product in productList) {
                productViewList.add(
                    convertToProductView(
                        product = product,
                        likeCount = product.likesList.size,
                        chatCount = product.roomList.size,
                        imageUrl = product.imageList.map { it.filePath }[0]
                    )
                )
            }
            return productViewList
        }
    }
}
