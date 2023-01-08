package me.youngjun.daangnmarket.api.product.controller

import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.api.product.service.ProductService
import me.youngjun.daangnmarket.common.domain.dto.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController(
    private val productService: ProductService,
) {

    @GetMapping("/product/list")
    fun productListView(
        @RequestParam(value = "area_id") areaId: Int,
    ): ApiResponse<List<ProductView>> {
        // TODO 스프링 시큐리티 ID 받아오기
        val productList = productService.getProductList(areaId)
        return ApiResponse.success(productList)
    }

}