package me.youngjun.daangnmarket.api.category.service

import me.youngjun.daangnmarket.api.category.dto.CategoryView
import me.youngjun.daangnmarket.common.domain.enum.CategoryEnum
import me.youngjun.daangnmarket.common.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {
    fun getCategoryList(): List<CategoryView> {
        val categoryViewList = mutableListOf<CategoryView>()
        CategoryEnum.values().forEach { category ->
            categoryViewList.add(CategoryView(category.code, category.detail))
        }
        return categoryViewList
    }

}