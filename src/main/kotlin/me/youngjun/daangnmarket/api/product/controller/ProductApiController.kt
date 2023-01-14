package me.youngjun.daangnmarket.api.product.controller

import me.youngjun.daangnmarket.api.product.dto.CategoryView
import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.api.product.service.ProductService
import me.youngjun.daangnmarket.common.domain.dto.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductApiController(
    private val productService: ProductService,
) {
    companion object {
        private val log = KotlinLogging.logger {}
    }

    @PostMapping("/api/v1/product")
    fun registerProduct(
        @RequestBody productRegisterDto: ProductRegisterDto,
        principal: Principal,
//        @AuthenticationPrincipal userDetail: UserDetailsImpl
    ): ResponseEntity<Any> {
        log.debug { "product Register [ user : $principal ]" }
        val memberId = principal.name.toLong()
        val product = productService.register(productRegisterDto, memberId)
        return ResponseEntity(product.id, HttpStatus.CREATED)
    }

    @GetMapping("/api/v1/product/list")
    fun getProductList(
        @RequestParam(value = "status", defaultValue = "1") areaId: Int? = 0,
    ): ApiResponse<List<ProductView>> {
        // TODO 스프링 시큐리티 ID 받아오기
        val productList = productService.getProductList(areaId)
        return ApiResponse.success(productList)
    }

    @GetMapping("/api/v1/category/list")
    fun getCategoryListView(): ApiResponse<List<CategoryView>> {
        val categoryList = productService.getCategoryList()
        return ApiResponse.success(categoryList)
    }
}