package com.example.coffeememos.search.data.repository

import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.data.mapper.SearchRecipeModelMapper
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.search.domain.repository.SearchRecipeDiskRepository
import javax.inject.Inject

class SearchRecipeDiskRepositoryImpl @Inject constructor()
    : SearchRecipeDiskRepository {
    @Inject
    lateinit var recipeDao: RecipeDao
    @Inject
    lateinit var mapper: SearchRecipeModelMapper

    override suspend fun searchRecipeByFreeWord(freeWord: String): List<SearchRecipeModel> {
        val recipeWithTasteList = recipeDao.getRecipeWithTasteByKeyword(freeWord)
        return mapper.execute(recipeWithTasteList)
    }

    override suspend fun getAllRecipe(): List<SearchRecipeModel> {
       val recipeWithTasteList = recipeDao.getRecipeWithTaste()
       return mapper.execute(recipeWithTasteList)
    }
}