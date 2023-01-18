package me.youngjun.daangnmarket.common.domain

import me.youngjun.daangnmarket.common.domain.enum.AreaEnum
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table
class Area(

    @Column(unique = true)
    val code: String? = AreaEnum.PANGYO.areaCode,

    @Column
    val name: String? = AreaEnum.PANGYO.areaName

) : BaseEntity() {

    @OneToMany(mappedBy = "areaId")
    private val _member: MutableList<Member> = mutableListOf()
    val members: List<Member> get() = _member.toList()

    @OneToMany(mappedBy = "areaId")
    private val _product: MutableList<Product> = mutableListOf()
    val products: List<Product> get() = _product.toList()

    companion object {
        fun of(
            areaCode: String
        ): Area {
            return Area(
                code = areaCode,
                name = AreaEnum.of(areaCode)!!.areaName
            )
        }
    }
}