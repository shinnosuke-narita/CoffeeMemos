package com.example.coffeememos.search.domain.repository

import com.example.coffeememos.entity.RecipeWithTaste

interface SearchRecipeRepository {
    suspend fun searchRecipeByFreeWord(freeWord: String): List<RecipeWithTaste>
}