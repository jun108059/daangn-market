package me.youngjun.daangnmarket.common.domain

import me.youngjun.daangnmarket.common.domain.enum.Role
import org.jetbrains.annotations.Nullable
import javax.persistence.*

@Entity
@Table
class Member(

    @Id
    @Nullable
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,

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
    var area: String? = "판교",

    @Column
    @Enumerated(EnumType.STRING)
    var role: Role? = Role.ROLE_USER,

    ) : BaseEntity() {

    fun bcryptPassword(encode: String) {
        this.password = encode
    }

}