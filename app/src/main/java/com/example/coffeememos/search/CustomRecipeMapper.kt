package com.example.coffeememos.search

import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.entity.RecipeWithTaste

class CustomRecipeMapper {
    // 簡易レシピリスト作成メソッド
    fun execute(recipeWithTasteList: List<RecipeWithTaste>): List<SearchRecipeModel> {
        if (recipeWithTasteList.isEmpty()) return listOf()

        val result = mutableListOf<SearchRecipeModel>()
        for (recipeWithTaste in recipeWithTasteList) {
            val recipe = recipeWithTaste.recipe
            val taste = recipeWithTaste.taste

            result.add(
                SearchRecipeModel(
                    recipe.id,
                    recipe.beanId,
                    taste.id,
                    recipe.country,
                    recipe.tool,
                    recipe.roast,
                    recipe.grindSize,
                    recipe.createdAt,
                    taste.sour,
                    taste.bitter,
                    taste.sweet,
                    taste.flavor,
                    taste.rich,
                    recipe.rating,
                    recipe.isFavorite
                )
            )
        }

        return result
    }
}