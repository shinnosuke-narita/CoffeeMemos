package com.example.coffeememos.search.data.repository

import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.domain.repository.SearchRecipeRepository

class SearchRecipeRepository(
    private val recipeDao: RecipeDao
) : SearchRecipeRepository {
    override suspend fun searchRecipeByFreeWord(freeWord: String): List<RecipeWithTaste> {
        return recipeDao.getRecipeWithTasteByKeyword(freeWord)
    }
}