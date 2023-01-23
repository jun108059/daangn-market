package me.youngjun.daangnmarket.api.category.controller

import me.youngjun.daangnmarket.api.category.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryApiController(
    private val categoryService: CategoryService
) {
    @GetMapping("/api/v1/category/list")
    fun getCategoryListView(
    ): ResponseEntity<Any> {
        val categoryList = categoryService.getCategoryList()
        return ResponseEntity.ok(categoryList)
    }
}