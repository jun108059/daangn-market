package me.youngjun.daangnmarket.api.product.dto

import me.youngjun.daangnmarket.common.domain.enum.Category

data class ProductRegisterDto(
    // @NotBlank(message = "제목을 입력해주세요")
    val title: String,

    val category: Category,

    // @NotBlank(message = "가격을 입력해주세요")
    val price: Int,

    // @NotBlank(message = "게시글 내용을 입력해주세요")
    val content: String,
)
