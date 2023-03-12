package me.youngjun.daangnmarket.api.product.controller

import me.youngjun.daangnmarket.api.likes.service.LikesService
import me.youngjun.daangnmarket.api.product.dto.*
import me.youngjun.daangnmarket.api.product.service.ProductService
import me.youngjun.daangnmarket.common.domain.ProductStatus
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
class ProductApiController(
    private val productService: ProductService,
    private val likesService: LikesService,
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
        @RequestParam("category_code") categoryCode: String?,
        @RequestParam("status") status: ProductStatus?,
        @RequestParam("likes") likes: Boolean?,
        @RequestParam("member_id") memberId: Long?,
        @RequestParam("search") searchKeyWord: String?,
        @RequestParam("page") page: Int?,
        @RequestParam("size") size: Int?,
    ): ResponseEntity<Page<ProductView>> {
        val filter = ProductFilterDto(
            categoryCode,
            status,
            likes,
            memberId,
            searchKeyWord,
        )
        val pageNum = page?.minus(1) ?: 0
        val pageSize = size ?: 10
        val pageRequest = PageRequest.of(pageNum, pageSize)
        val requestMemberId = principal.name.toLong()
        val productList = productService.getProductList(requestMemberId, filter, pageRequest)
        return ResponseEntity.ok(productList)
    }

    @GetMapping("/api/v1/product")
    fun getProduct(
        principal: Principal,
        @RequestParam("product_id") productId: Long,
    ): ResponseEntity<ProductDetailView> {
        val productDetailView = productService.getProduct(productId)
        productDetailView.isLikes = likesService.getLikes(
            memberId = principal.name.toLong(),
            productId = productId,
        ) != null
        productDetailView.isMine = productDetailView.memberId == principal.name.toLong()
        return ResponseEntity.ok(productDetailView)
    }

    @DeleteMapping("/api/v1/product")
    fun deleteProduct(
        @RequestParam("product_id") productId: Long,
    ): ResponseEntity<Any> {
        productService.deleteProduct(productId)
        return ResponseEntity.ok("productId [$productId] delete Ok")
    }

    @PutMapping("/api/v1/product")
    fun updateProduct(
        @RequestBody productUpdateDto: ProductUpdateDto,
    ): ResponseEntity<Any> {
        productService.updateProduct(productUpdateDto)
        return ResponseEntity.ok("productId [${productUpdateDto.id}] update Ok")
    }

    @PutMapping("/api/v1/product/status")
    fun updateProductStatus(
        @RequestBody productStatusDto: ProductStatusUpdateDto,
    ): ResponseEntity<String> {
        productService.updateProductStatus(productStatusDto)
        return ResponseEntity.ok("productId [${productStatusDto.id}] status update Ok")
    }
}
