package me.youngjun.daangnmarket.api.product.service

import me.youngjun.daangnmarket.api.product.dto.CategoryView
import me.youngjun.daangnmarket.api.product.dto.ProductDetailView
import me.youngjun.daangnmarket.api.product.dto.ProductRegisterDto
import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.api.product.mapper.ProductViewMapper
import me.youngjun.daangnmarket.common.domain.Image
import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.domain.Product
import me.youngjun.daangnmarket.common.domain.enum.CategoryEnum
import me.youngjun.daangnmarket.common.repository.*
import me.youngjun.daangnmarket.infra.exception.ErrorCode
import me.youngjun.daangnmarket.infra.exception.NotFoundAreaException
import me.youngjun.daangnmarket.infra.exception.NotFoundException
import me.youngjun.daangnmarket.infra.exception.NotFoundMemberException
import mu.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val memberRepository: MemberRepository,
    private val areaRepository: AreaRepository,
    private val categoryRepository: CategoryRepository,
    private val imageRepository: ImageRepository,
    private val likesRepository: LikesRepository,
    private val roomRepository: RoomRepository,
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
        val area = areaRepository.findByIdOrNull(registerDto.areaId)
            ?: throw NotFoundAreaException(ErrorCode.AREA_NOT_FOUND)
        val category = categoryRepository.findByIdOrNull(registerDto.categoryId)
            ?: throw NotFoundException(ErrorCode.CATEGORY_NOT_FOUND)
        val productEntity = Product.convertToEntity(registerDto, member)
        productEntity.areaId = area
        productEntity.categoryId = category
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
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw NotFoundMemberException(ErrorCode.DEFAULT_NOT_FOUND)
        val productList = productRepository.findByAreaId(member.areaId)
        val productViewList = mutableListOf<ProductView>()
        for (product in productList) {
            // TODO QueryDSL 적용
            val findByProductId = likesRepository.findByProductId(product.id!!)
            val likeCount = findByProductId.size
            val imageList = imageRepository.findByProductId(product.id!!)
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
        CategoryEnum.values().forEach { category ->
            categoryViewList.add(CategoryView(category.code, category.detail))
        }
        return categoryViewList
    }


    fun getProduct(
        productId: Long
    ): ProductDetailView {
        val product = productRepository.findByIdOrNull(productId)
            ?: throw NotFoundException(ErrorCode.PRODUCT_NOT_FOUND)
        // Image 정보 가져오기
        val imageList = imageRepository.findByProductId(productId)
        val imagePathList = imageList.map { it.filePath }.toList()
        // member 정보 가져오기
        val member = memberRepository.findByIdOrNull(product.member.id)
            ?: throw NotFoundMemberException(ErrorCode.DEFAULT_NOT_FOUND)
        val category = product.categoryId.name
            ?: throw NotFoundException(ErrorCode.CATEGORY_NOT_FOUND)
        val roomList = roomRepository.findByProductId(product)
        val likeList = likesRepository.findByProductId(productId)
        return ProductDetailView(
            imagePaths = imagePathList,
            imagePathCount = imagePathList.size,
            nickname = member.nickname,
            title = product.title,
            price = product.price,
            category = category,
            createAt = product.createdAt!!,
            content = product.content,
            chatCount = roomList.size,
            likesCount = likeList.size
        )
    }
}