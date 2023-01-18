package me.youngjun.daangnmarket.common.domain

import javax.persistence.*

@Entity
@Table
class Image(

    @Column
    var filePath: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
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