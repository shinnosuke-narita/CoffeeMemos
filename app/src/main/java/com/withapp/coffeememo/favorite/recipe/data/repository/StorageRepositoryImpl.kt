package com.withapp.coffeememo.favorite.recipe.data.repository

import com.withapp.coffeememo.core.data.dao.RecipeDao
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor()
    : StorageRepository {
    @Inject
    lateinit var recipeDao: RecipeDao

    override suspend fun getFavoriteRecipe(): List<Recipe> {
        return recipeDao.getFavoriteRecipe()
    }

    override suspend fun deleteFavorite(recipeId: Long) {
        recipeDao.updateFavoriteByRecipeId(recipeId, false)
    }
}