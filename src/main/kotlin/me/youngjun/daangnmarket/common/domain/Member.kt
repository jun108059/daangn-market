package me.youngjun.daangnmarket.common.domain

import me.youngjun.daangnmarket.api.member.dto.MemberJoinRequestDto
import me.youngjun.daangnmarket.common.domain.enum.Role
import javax.persistence.*

@Entity
@Table
class Member(

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
    var imagePath: String = "",

    @Column
    @Enumerated(EnumType.STRING)
    var role: Role? = Role.ROLE_USER,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", referencedColumnName = "id")
    var areaId: Area = Area.of("0001")

) : BaseEntity() {

    fun bcryptPassword(encode: String) {
        this.password = encode
    }

    companion object {
        fun convertToEntity(
            form: MemberJoinRequestDto
        ): Member {
            return Member(
                email = form.email,
                password = form.password,
                name = form.name,
                phone = form.phone,
                nickname = form.nickname,
                role = form.role
            )
        }
    }
}
