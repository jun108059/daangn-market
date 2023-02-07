package me.youngjun.daangnmarket.api.product.dto

data class ProductView(
    val id: Long,
    val areaName: String,
    val title: String,
    val price: Long,
    val likeCount: Int,
    val chatCount: Int,
    val productStatus: String,
    val imgUrl: String
)
