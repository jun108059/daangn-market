package me.youngjun.daangnmarket.api.product.persistence

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import me.youngjun.daangnmarket.api.product.dto.ProductView
import me.youngjun.daangnmarket.api.product.mapper.ProductViewMapper
import me.youngjun.daangnmarket.common.domain.*
import me.youngjun.daangnmarket.common.domain.QLikes.likes
import me.youngjun.daangnmarket.common.domain.QProduct.product
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class ProductRepositorySupport(
    private val queryFactory: JPAQueryFactory
) : QuerydslRepositorySupport(Product::class.java) {
    fun findAll(): List<Product> {
        return queryFactory
            .selectFrom(product)
            .fetch()
    }

    fun findByFilter(
        area: Area?,
        category: Category?,
        status: ProductStatus?,
        member: Member?,
        isLike: Boolean,
        searchKeyWord: String?,
        pageable: PageRequest
    ): Page<ProductView> {
        if (isLike) {
            val products = queryFactory
                .selectFrom(product)
                .join(product._likes, likes)
                .where(
                    eqStatus(status),
                    eqLikeMember(member)
                )
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

            val countQuery = queryFactory
                .select(product.count())
                .from(product)
                .join(product._likes, likes)
                .where(
                    eqStatus(status),
                    eqLikeMember(member)
                )
            val convertViewList = ProductViewMapper.convertViewList(products)
            return PageableExecutionUtils.getPage(convertViewList, pageable) { countQuery.fetchOne()!! }
        } else {
            val products = queryFactory
                .selectFrom(product)
                .where(
                    eqArea(area),
                    eqCategory(category),
                    eqStatus(status),
                    eqMember(member),
                    containsTitle(searchKeyWord)
                )
                .offset(pageable.offset)
                .limit(pageable.pageSize.toLong())
                .fetch()

            val countQuery = queryFactory
                .select(product.count())
                .from(product)
                .where(
                    eqArea(area),
                    eqCategory(category),
                    eqStatus(status),
                    eqMember(member),
                    containsTitle(searchKeyWord)
                )
            val convertViewList = ProductViewMapper.convertViewList(products)
            return PageableExecutionUtils.getPage(convertViewList, pageable) { countQuery.fetchOne()!! }
        }
    }

    private fun eqArea(area: Area?): BooleanExpression? {
        return if (area == null) {
            null
        } else product.areaId.eq(area)
    }

    private fun eqCategory(category: Category?): BooleanExpression? {
        return if (category == null) {
            null
        } else product.categoryId.eq(category)
    }

    private fun eqStatus(status: ProductStatus?): BooleanExpression? {
        return if (status == null) {
            null
        } else product.status.eq(status)
    }

    private fun eqMember(member: Member?): BooleanExpression? {
        return if (member == null) {
            null
        } else product.member.eq(member)
    }

    private fun eqLikeMember(member: Member?): BooleanExpression? {
        return if (member == null) {
            null
        } else likes.member.eq(member)
    }

    private fun containsTitle(searchKeyWord: String?): BooleanExpression? {
        return if (searchKeyWord.isNullOrBlank()) {
            null
        } else product.title.contains(searchKeyWord)
    }
}
