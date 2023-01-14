package me.youngjun.daangnmarket.api.product.service

import me.youngjun.daangnmarket.api.product.dto.CategoryView
import me.youngjun.daangnmarket.api.product.dto.ProductRegisterDto
import me.youngjun.daangnmarket.api.product.dto.ProductView
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