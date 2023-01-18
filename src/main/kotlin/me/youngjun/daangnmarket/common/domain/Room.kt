package me.youngjun.daangnmarket.common.domain

import javax.persistence.*

@Entity
@Table
class Room(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    var productId: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    var memberId: Member,

    ) : BaseEntity()