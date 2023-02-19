package com.example.coffeememos.search.domain.repository

import com.example.coffeememos.search.domain.model.SearchRecipeModel

interface SearchRecipeDiskRepository {
    /**
     *  キーワード検索
     */
    suspend fun searchRecipeByFreeWord(freeWord: String): List<SearchRecipeModel>

    /**
     * 全件取得
     */
    suspend fun getAllRecipe(): List<SearchRecipeModel>
}