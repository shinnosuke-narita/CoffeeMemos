package com.example.coffeememos.search.data.repository

import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.domain.repository.SearchRecipeRepository
import javax.inject.Inject

class SearchRecipeRepositoryImpl @Inject constructor()
    : SearchRecipeRepository {
    @Inject
    lateinit var recipeDao: RecipeDao

    override suspend fun searchRecipeByFreeWord(freeWord: String): List<RecipeWithTaste> {
        return recipeDao.getRecipeWithTasteByKeyword(freeWord)
    }
}