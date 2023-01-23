package me.youngjun.daangnmarket.api.product.controller

import me.youngjun.daangnmarket.api.product.dto.ProductRegisterDto
import me.youngjun.daangnmarket.api.product.dto.ProductUpdateDto
import me.youngjun.daangnmarket.api.product.service.ProductService
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

    @GetMapping("/api/v1/product")
    fun getProduct(
        @RequestParam("product_id") productId: Long
    ): ResponseEntity<Any> {
        val productDetailView = productService.getProduct(productId)
        return ResponseEntity.ok(productDetailView)
    }

    @DeleteMapping("/api/v1/product")
    fun deleteProduct(
        @RequestParam("product_id") productId: Long
    ): ResponseEntity<Any> {
        productService.deleteProduct(productId)
        return ResponseEntity.ok("productId [$productId] delete Ok")
    }


}