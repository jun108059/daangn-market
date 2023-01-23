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

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
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

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _image: MutableList<Image> = mutableListOf()
    val imageList: List<Image> get() = _image.toList()

    @OneToMany(mappedBy = "productId", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _room: MutableList<Room> = mutableListOf()
    val roomList: List<Room> get() = _room.toList()

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _likes: MutableList<Likes> = mutableListOf()
    val likesList: List<Likes> get() = _likes.toList()

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
