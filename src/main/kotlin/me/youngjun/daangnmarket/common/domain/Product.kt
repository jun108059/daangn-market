package me.youngjun.daangnmarket.common.domain

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
    var name: String,

    @Column
    var content: String,

    @Column
    @Enumerated(EnumType.STRING)
    var category: Category,

    @Column
    @Enumerated(EnumType.STRING)
    var status: ProductStatus? = ProductStatus.TRADING,

    ) : BaseEntity()
