package me.youngjun.daangnmarket.common.domain

import me.youngjun.daangnmarket.api.product.dto.ProductRegisterDto
import me.youngjun.daangnmarket.common.domain.enum.ProductStatus
import javax.persistence.*

@Entity
@Table
class Product(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    var member: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    var areaId: Area = Area.of("0001"),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    var categoryId: Category = Category.of("0"),

    @Column
    var title: String,

    @Column
    @Enumerated(EnumType.STRING)
    var status: ProductStatus? = ProductStatus.TRADING,

    @Column
    var content: String,

    @Column
    var price: Long

) : BaseEntity() {
    companion object {
        fun convertToEntity(
            productRegisterDto: ProductRegisterDto,
            member: Member
        ): Product {
            return Product(
                member = member,
                title = productRegisterDto.title,
                content = productRegisterDto.content,
                price = productRegisterDto.price,
            )
        }

    }
}
