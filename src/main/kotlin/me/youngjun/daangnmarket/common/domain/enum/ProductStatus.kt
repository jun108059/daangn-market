package me.youngjun.daangnmarket.common.domain.enum

enum class ProductStatus(
    val value: String
) {
    TRADING("판매중"),
    RESERVED("예약중"),
    COMPLETED("거래완료");
}