package me.youngjun.daangnmarket.api.product.service

import me.youngjun.daangnmarket.api.category.service.CategoryService
import me.youngjun.daangnmarket.api.image.service.ImageService
import me.youngjun.daangnmarket.api.product.dto.*
import me.youngjun.daangnmarket.api.product.persistence.ProductRepositorySupport
import me.youngjun.daangnmarket.common.domain.Area
import me.youngjun.daangnmarket.common.domain.Image
import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.domain.Product
import me.youngjun.daangnmarket.common.domain.enum.AreaEnum
import me.youngjun.daangnmarket.common.repository.AreaRepository
import me.youngjun.daangnmarket.common.repository.ImageRepository
import me.youngjun.daangnmarket.common.repository.MemberRepository
import me.youngjun.daangnmarket.common.repository.ProductRepository
import me.youngjun.daangnmarket.infra.exception.ErrorCode
import me.youngjun.daangnmarket.infra.exception.NotFoundAreaException
import me.youngjun.daangnmarket.infra.exception.NotFoundException
import me.youngjun.daangnmarket.infra.exception.NotFoundMemberException
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productRepositorySupport: ProductRepositorySupport,
    private val memberRepository: MemberRepository,
    private val areaRepository: AreaRepository,
    private val categoryService: CategoryService,
    private val imageRepository: ImageRepository,
    private val imageService: ImageService,
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
        val category = categoryService.getCategory(registerDto.categoryId)
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
        memberId: Long,
        filter: ProductFilterDto,
        pageable: PageRequest,
    ): Page<ProductView> {
        var area: Area? = null
        var member: Member? = null

        // memberId 기반 필터는 area 미적용
        if (filter.memberId != null) {
            member = memberRepository.findByIdOrNull(filter.memberId)
                ?: throw NotFoundMemberException(ErrorCode.DEFAULT_NOT_FOUND)
        } else {
            val tempMember = memberRepository.findByIdOrNull(memberId)
                ?: throw NotFoundMemberException(ErrorCode.DEFAULT_NOT_FOUND)
            area = tempMember.areaId
        }

        val category = if (filter.categoryId != null) {
            categoryService.getCategory(filter.categoryId)
        } else {
            null
        }

        return productRepositorySupport.findByFilter(
            area = area,
            category = category,
            status = filter.status,
            member = member,
            isLike = filter.likes ?: false,
            searchKeyWord = filter.searchKeyWord,
            pageable = pageable,
        )
    }

    fun getProduct(
        productId: Long,
    ): ProductDetailView {
        val product = productRepository.findByIdOrNull(productId)
            ?: throw NotFoundException(ErrorCode.PRODUCT_NOT_FOUND)
        val imageUrlList = product.imageList.map { it.filePath }
        val category = product.categoryId.name
            ?: throw NotFoundException(ErrorCode.CATEGORY_NOT_FOUND)
        return ProductDetailView(
            id = product.id!!,
            memberId = product.member.id!!,
            areaName = AreaEnum.getName(product.areaId.code),
            title = product.title,
            price = product.price,
            likesCount = product.likesList.size,
            chatCount = product.roomList.size,
            status = product.status!!,
            createdDateTime = product.createdAt!!,
            imageUrls = imageUrlList,
            nickname = product.member.nickname,
            category = category,
            content = product.content,
            sellerImageUrl = product.member.imagePath,
            isLikes = null,
            isMine = null,
        )
    }

    @Transactional
    fun deleteProduct(
        productId: Long,
    ) {
        productRepository.deleteById(productId)
    }

    @Transactional
    fun updateProduct(
        updateDto: ProductUpdateDto,
    ) {
        val product = productRepository.findByIdOrNull(updateDto.id)
            ?: throw NotFoundException(ErrorCode.PRODUCT_NOT_FOUND)

        val imageList = mutableListOf<Image>()
        updateDto.imageList.map {
            imageList.add(Image(it, product))
        }
        imageService.updateImages(
            productId = product.id!!,
            imageList = imageList,
        )
        val category = categoryService.getCategory(updateDto.categoryId)

        product.title = updateDto.title
        product.categoryId = category
        product.price = updateDto.price
        product.content = updateDto.content
        product.status = updateDto.status

        productRepository.save(product)
    }
}
