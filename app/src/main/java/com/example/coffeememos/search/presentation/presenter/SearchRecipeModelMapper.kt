package com.example.coffeememos.search.presentation.presenter

import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.entity.RecipeWithTaste

class SearchRecipeModelMapper {
    // 簡易レシピリスト作成メソッド
    fun execute(recipeWithTasteList: List<RecipeWithTaste>): List<SearchRecipeModel> {
        return recipeWithTasteList.map { recipeWithTaste ->
            val recipe = recipeWithTaste.recipe
            val taste = recipeWithTaste.taste

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
        }
    }
}