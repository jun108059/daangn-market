package me.youngjun.daangnmarket.infra.domain

import javax.persistence.*

@Entity
@Table
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column
    var email: String,

    @Column
    var password: String,

    @Column
    var name: String,

    @Column
    var phone: String,

    @Column
    var nickname: String,

    @Column
    var area: String,

    // ROLE (작성자)

    ) : BaseEntity()