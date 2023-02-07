package me.youngjun.daangnmarket.common.domain

import javax.persistence.*

@Entity
@Table
class Likes(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    val member: Member

) : BaseEntity()
