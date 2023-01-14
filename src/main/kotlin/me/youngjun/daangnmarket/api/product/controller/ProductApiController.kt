package me.youngjun.daangnmarket.api.product.controller

import me.youngjun.daangnmarket.api.product.dto.CategoryView
import me.youngjun.daangnmarket.api.product.dto.ProductRegisterDto
import me.youngjun.daangnmarket.api.product.service.ProductService
import me.youngjun.daangnmarket.common.domain.dto.ApiResponse
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

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
        principal: Principal,
    ): ResponseEntity<Any> {
        val memberId = principal.name.toLong()
        val productList = productService.getProductList(memberId)
        return ResponseEntity.ok(productList)
    }

    @GetMapping("/api/v1/category/list")
    fun getCategoryListView(
    ): ApiResponse<List<CategoryView>> {
        val categoryList = productService.getCategoryList()
        return ApiResponse.success(categoryList)
    }
}