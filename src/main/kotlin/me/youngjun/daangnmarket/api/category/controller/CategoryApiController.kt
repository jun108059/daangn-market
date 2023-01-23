package me.youngjun.daangnmarket.api.category.controller

import me.youngjun.daangnmarket.api.category.dto.CategoryView
import me.youngjun.daangnmarket.api.category.service.CategoryService
import me.youngjun.daangnmarket.common.domain.dto.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
class CategoryApiController(
    private val categoryService: CategoryService
) {
    @GetMapping("/api/v1/category/list")
    fun getCategoryListView(
    ): ApiResponse<List<CategoryView>> {
        val categoryList = categoryService.getCategoryList()
        return ApiResponse.success(categoryList)
    }
}