package me.youngjun.daangnmarket.api.product.service

import me.youngjun.daangnmarket.api.product.dto.CategoryView
import me.youngjun.daangnmarket.api.product.dto.ProductRegisterDto
import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.api.product.mapper.ProductViewMapper
import me.youngjun.daangnmarket.common.domain.Image
import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.domain.Product
import me.youngjun.daangnmarket.common.domain.enum.Category
import me.youngjun.daangnmarket.common.repository.ImageRepository
import me.youngjun.daangnmarket.common.repository.LikesRepository
import me.youngjun.daangnmarket.common.repository.MemberRepository
import me.youngjun.daangnmarket.common.repository.ProductRepository
import me.youngjun.daangnmarket.infra.exception.ErrorCode
import me.youngjun.daangnmarket.infra.exception.NotFoundMemberException
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
    private val imageRepository: ImageRepository,
    private val likesRepository: LikesRepository,
) {
    companion object {
        private val log = KotlinLogging.logger {}
    }

    @Transactional
    fun register(
        registerDto: ProductRegisterDto,
        memberId: Long,
    ): Product {
        val member: Member = memberRepository.findByIdOrNull(memberId)
            ?: throw NotFoundMemberException(ErrorCode.DEFAULT_NOT_FOUND)
        val productEntity = Product.convertToEntity(registerDto, member)
        val savedProduct = productRepository.save(productEntity)

        registerDto.imageList.forEach { image ->
            val imageEntity = Image.convertToEntity(savedProduct, image)
            log.info { "image : $image" }
            imageRepository.save(imageEntity)
        }

        return savedProduct
    }

    @Transactional(readOnly = true)
    fun getProductList(
        memberId: Long
    ): List<ProductView> {
        val productList = productRepository.findAll()
        // TODO productRepository.findByArea(areaId) 회원가입 시 area 받으면 변경
        val productViewList = mutableListOf<ProductView>()
        for (product in productList) {
            // TODO QueryDSL 적용
            val findByProductId = likesRepository.findByProductId(product.id!!)
            val likeCount = findByProductId.size
            val imageList = imageRepository.findByProductId(product.id)
            var imageUrl = ""
            if (imageList.isNotEmpty()) {
                imageUrl = imageList[0].filePath
            }
            productViewList.add(
                ProductViewMapper.convertToProductView(
                    product = product,
                    likeCount = likeCount,
                    chatCount = 3, // TODO chat 구현 후 추가
                    imageUrl = imageUrl
                )
            )
        }
        return productViewList
    }

    fun getCategoryList(): List<CategoryView> {
        val categoryViewList = mutableListOf<CategoryView>()
        Category.values().forEach { category ->
            categoryViewList.add(CategoryView(category.categoryCode, category.categoryName))
        }
        return categoryViewList
    }
}