package me.youngjun.daangnmarket.api

import com.querydsl.jpa.impl.JPAQueryFactory
import me.youngjun.daangnmarket.common.domain.Area
import me.youngjun.daangnmarket.common.domain.Category
import me.youngjun.daangnmarket.common.domain.Member
import me.youngjun.daangnmarket.common.domain.Product
import me.youngjun.daangnmarket.common.domain.ProductStatus
import me.youngjun.daangnmarket.common.domain.QProduct.product
import me.youngjun.daangnmarket.common.domain.enum.Role
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@SpringBootTest
@Transactional
class QuerydslTest {

    @Autowired
    lateinit var em: EntityManager

    @Autowired
    lateinit var queryFactory: JPAQueryFactory

    @BeforeEach
    fun before() {
        queryFactory = JPAQueryFactory(em)

        val areaA = Area.of("0001")
        em.persist(areaA)

        val memberA = Member(
            email = "yj@test.com",
            password = "1234",
            name = "park",
            phone = "010-1234-1234",
            nickname = "abc",
            imagePath = "src/image.png",
            role = Role.ROLE_USER,
            areaId = areaA
        )
        em.persist(memberA)

        val productEntityA = Product(
            member = memberA,
            areaId = areaA,
            categoryId = Category.of("1"),
            title = "안녕",
            status = ProductStatus.TRADING,
            content = "판매중",
            price = 1000
        )
        val productEntityB = Product(
            member = memberA,
            areaId = areaA,
            categoryId = Category.of("1"),
            title = "안녕2",
            status = ProductStatus.TRADING,
            content = "판매중2",
            price = 1000
        )

        em.persist(productEntityA)
        em.persist(productEntityB)
    }

    @Test
    fun querydslForProduct() {
        val findProduct = queryFactory
            .selectFrom(product)
            .from(product)
            .where(product.title.eq("안녕"))
            .fetchOne()
            ?: throw NullPointerException("product not found")

        Assertions.assertEquals(findProduct.title, "안녕")
    }
}
