package com.withapp.coffeememo.search.recipe.data.repository

import com.withapp.coffeememo.core.data.dao.RecipeDao
import com.withapp.coffeememo.core.data.entity.RecipeWithTaste
import com.withapp.coffeememo.search.recipe.data.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.repository.SearchRecipeDiskRepository
import javax.inject.Inject

class SearchRecipeDiskRepositoryImpl @Inject constructor()
    : SearchRecipeDiskRepository {
    @Inject
    lateinit var recipeDao: RecipeDao
    @Inject
    lateinit var mapper: SearchRecipeModelMapper

    override suspend fun searchRecipeByFreeWord(freeWord: String): List<SearchRecipeModel> {
        val recipeWithTasteList = recipeDao.getRecipeWithTasteByKeyword(freeWord)

        return getSearchRecipeModelList(recipeWithTasteList)
    }

    override suspend fun getAllRecipe(): List<SearchRecipeModel> {
       val recipeWithTasteList = recipeDao.getRecipeWithTaste()
       return getSearchRecipeModelList(recipeWithTasteList)
    }

    override suspend fun updateFavorite(recipeId: Long, isFavorite: Boolean) {
        recipeDao.updateFavoriteByRecipeId(recipeId, isFavorite)
    }

    private fun getSearchRecipeModelList(
        recipeWithTasteList: List<RecipeWithTaste>
    ): List<SearchRecipeModel> {
        val result: MutableList<SearchRecipeModel> = mutableListOf()
        for (recipeWithTaste in recipeWithTasteList) {
            result.add(
                mapper.execute(recipeWithTaste)
            )
        }

        return result
    }
}