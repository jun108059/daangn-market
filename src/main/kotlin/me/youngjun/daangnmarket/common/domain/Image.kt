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
    var filePath: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    ) : BaseEntity() {

    companion object {
        fun convertToEntity(
            product: Product,
            imagePath: String,
        ): Image {
            return Image(
                filePath = imagePath,
                product = product
            )
        }

    }
}