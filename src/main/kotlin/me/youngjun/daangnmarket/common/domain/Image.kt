package me.youngjun.daangnmarket.common.domain

import javax.persistence.*

@Entity
@Table
class Image(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    val id: Long? = null,

    @Column
    var fileName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    ) : BaseEntity()