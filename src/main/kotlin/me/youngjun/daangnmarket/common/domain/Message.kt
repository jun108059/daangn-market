package me.youngjun.daangnmarket.common.domain

import javax.persistence.*

@Entity
@Table
class Message(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    var roomId: Room,

    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    var memberId: Member,

    @Column
    var content: String,

    @Column(columnDefinition = "tinyint", length = 1)
    var isRead: Int

) : BaseEntity()
