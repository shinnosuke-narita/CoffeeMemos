package com.example.coffeememos.search.recipe.domain.repository

import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel

interface SearchRecipeDiskRepository {
    /**
     *  キーワード検索
     */
    suspend fun searchRecipeByFreeWord(freeWord: String): List<SearchRecipeModel>
    /**
     * 全件取得
     */
    suspend fun getAllRecipe(): List<SearchRecipeModel>
    /**
     * お気に入り更新
     */
    suspend fun updateFavorite(recipeId: Long, isFavorite: Boolean)
}