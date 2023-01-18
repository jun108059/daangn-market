package me.youngjun.daangnmarket.common.domain

import me.youngjun.daangnmarket.common.domain.enum.CategoryEnum
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table
class Category(

    @Column(unique = true)
    val code: String? = CategoryEnum.DIGITAL_DEVICE.code,

    @Column
    val name: String? = CategoryEnum.DIGITAL_DEVICE.detail

) : BaseEntity() {

    @OneToMany(mappedBy = "categoryId")
    private val _product: MutableList<Product> = mutableListOf()
    val products: List<Product> get() = _product.toList()

    companion object {
        fun of(
            categoryCode: String
        ): Category {
            return Category(
                code = categoryCode,
                name = CategoryEnum.of(categoryCode)!!.detail
            )
        }

    }
}