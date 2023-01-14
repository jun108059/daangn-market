package me.youngjun.daangnmarket.common.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import me.youngjun.daangnmarket.api.product.dto.ProductRegisterDto
import me.youngjun.daangnmarket.common.domain.enum.Area
import me.youngjun.daangnmarket.common.domain.enum.Category
import me.youngjun.daangnmarket.common.domain.enum.ProductStatus
import javax.persistence.*

@Entity
@Table
class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    val id: Long? = null,

    @Column
    var title: String,

    @Column
    var price: Long,

    @Column
    var content: String,

    @Column
    @Enumerated(EnumType.STRING)
    var category: Category,

    @Column
    @Enumerated(EnumType.STRING)
    var status: ProductStatus? = ProductStatus.TRADING,

    @Column
    @Enumerated(EnumType.STRING)
    var area: Area? = Area.PANGYO,

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    var member: Member,

    ) : BaseEntity() {
    companion object {
        fun convertToEntity(
            productRegisterDto: ProductRegisterDto,
            member: Member
        ): Product {
            return Product(
                id = 0,
                title = productRegisterDto.title,
                price = productRegisterDto.price,
                content = productRegisterDto.content,
                category = productRegisterDto.category,
                member = member
            )
        }

    }
}
