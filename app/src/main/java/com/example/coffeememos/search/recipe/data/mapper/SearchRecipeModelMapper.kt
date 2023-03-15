package com.example.coffeememos.search.recipe.data.mapper

import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.recipe.domain.model.SearchRecipeModel
import javax.inject.Inject

class SearchRecipeModelMapper @Inject constructor() {
    // 簡易レシピリスト作成メソッド
    fun execute(recipeWithTaste: RecipeWithTaste): SearchRecipeModel {
        val recipe = recipeWithTaste.recipe
        val taste = recipeWithTaste.taste

        return SearchRecipeModel(
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
    }
}