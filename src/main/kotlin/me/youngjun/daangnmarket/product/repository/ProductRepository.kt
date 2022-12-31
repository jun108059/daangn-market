package me.youngjun.daangnmarket.product.repository

import me.youngjun.daangnmarket.infra.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long> {

}