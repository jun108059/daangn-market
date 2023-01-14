package me.youngjun.daangnmarket.common.domain

import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.common.domain.enum.Role
import javax.persistence.*

@Entity
@Table
class Member(

    @Id
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

    companion object {
        fun convertToEntity(
            form: MemberJoinRequestDto
        ): Member {
            return Member(
                id = 0,
                email = form.email,
                password = form.password,
                name = form.name,
                phone = form.phone,
                nickname = form.nickname,
                area = form.area,
                role = form.role
            )
        }
    }
}