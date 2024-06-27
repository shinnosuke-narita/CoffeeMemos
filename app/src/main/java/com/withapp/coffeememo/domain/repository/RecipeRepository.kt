package com.withapp.coffeememo.domain.repository

import com.withapp.coffeememo.infra.data.entity.Recipe
import com.withapp.coffeememo.infra.data.entity.RecipeWithTaste
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun insert(recipe: Recipe)

    suspend fun insertAll(recipes: List<Recipe>)

    suspend fun update(recipe: Recipe)

    suspend fun updateFavoriteByRecipeId(
        id: Long,
        favoriteFlag: Boolean)

    suspend fun updateCountryById(
        id: Long,
        country: String)

    suspend fun deleteById(id: Long)

    suspend fun getNewestRecipeId(): Long

    suspend fun getRecipeById(id: Long): Recipe

    suspend fun getRecipeIdsByBeanId(beanId: Long): List<Long>

    fun getTotalCount(): Int

    fun getRecipeWithTasteByFlow(): Flow<List<RecipeWithTaste>>

    suspend fun getRecipeWithTaste(): List<RecipeWithTaste>

    suspend fun getRecipeWithTasteByKeyword(
        keyword: String
    ): List<RecipeWithTaste>

    suspend fun getFavoriteRecipe(): List<Recipe>
}