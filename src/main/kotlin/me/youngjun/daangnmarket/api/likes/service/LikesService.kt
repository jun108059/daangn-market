package me.youngjun.daangnmarket.api.likes.service

import me.youngjun.daangnmarket.common.domain.Likes
import me.youngjun.daangnmarket.common.repository.LikesRepository
import me.youngjun.daangnmarket.common.repository.MemberRepository
import me.youngjun.daangnmarket.common.repository.ProductRepository
import me.youngjun.daangnmarket.infra.exception.DuplicateException
import me.youngjun.daangnmarket.infra.exception.ErrorCode
import me.youngjun.daangnmarket.infra.exception.NotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class LikesService(
    private val likesRepository: LikesRepository,
    private val memberRepository: MemberRepository,
    private val productRepository: ProductRepository,
) {

    fun addLikes(
        memberId: Long,
        productId: Long
    ): Long {
        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw NotFoundException(ErrorCode.MEMBER_NOT_FOUND)
        val product = productRepository.findByIdOrNull(productId)
            ?: throw NotFoundException(ErrorCode.PRODUCT_NOT_FOUND)
        if (likesRepository.existsByMemberIdAndProductId(memberId, productId)) {
            throw DuplicateException(ErrorCode.DUPLICATE_LIKES)
        }
        val likesEntity = Likes(product, member)
        val savedEntity = likesRepository.save(likesEntity)

        return savedEntity.id ?: 0
    }

}