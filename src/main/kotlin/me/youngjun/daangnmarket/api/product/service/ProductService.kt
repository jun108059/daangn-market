package me.youngjun.daangnmarket.api.product.service

import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.common.domain.enum.ProductStatus
import me.youngjun.daangnmarket.common.repository.ProductRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository
) {
    companion object {
        private val log = KotlinLogging.logger {}
    }

    fun getProductList(
        areaId: Int
    ): List<ProductView> {
        // TODO productRepository.findByArea(areaId)
        val productViewA = ProductView(
            id = 1,
            areaName = "판교",
            title = "a",
            price = 100,
            likeCount = 20,
            productStatus = ProductStatus.TRADING,
            thumbnail = "/s3/url"
        )

        val productViewB = ProductView(
            id = 2,
            areaName = "판교",
            title = "b",
            price = 200,
            likeCount = 15,
            productStatus = ProductStatus.COMPLETED,
            thumbnail = "/s3/abc/url"
        )
        return listOf(productViewA, productViewB)
    }
}