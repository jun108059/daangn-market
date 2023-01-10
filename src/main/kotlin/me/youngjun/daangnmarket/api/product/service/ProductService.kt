package me.youngjun.daangnmarket.api.product.service

import me.youngjun.daangnmarket.api.product.dto.CategoryView
import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.common.domain.enum.Category
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
        areaId: Int?
    ): List<ProductView> {
        // TODO productRepository.findByArea(areaId)
        val productViewA = ProductView(
            id = 1,
            areaName = "판교",
            title = "당근 인형 팔아요",
            price = 100,
            likeCount = 20,
            chatCount = 10,
            productStatus = ProductStatus.TRADING,
            imgUrl = "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net%2F20220520_198%2F16530412329208sUlC_JPEG%2F1R3811668.jpg&type=a340"
        )

        val productViewB = ProductView(
            id = 2,
            areaName = "판교",
            title = "당근 인형 2배로 비싸게 팔아요",
            price = 200,
            likeCount = 15,
            chatCount = 5,
            productStatus = ProductStatus.COMPLETED,
            imgUrl = "https://search.pstatic.net/common/?src=http%3A%2F%2Fshop1.phinf.naver.net%2F20220520_198%2F16530412329208sUlC_JPEG%2F1R3811668.jpg&type=a340"
        )
        return listOf(productViewA, productViewB)
    }

    fun getCategoryList(): List<CategoryView> {
        val categoryViewList = mutableListOf<CategoryView>()
        Category.values().forEach { category ->
            categoryViewList.add(CategoryView(category.categoryCode, category.categoryName))
        }
        return categoryViewList
    }
}