package com.withapp.coffeememo.favorite.recipe.presentation.presenter

import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.presentation.model.FavoriteRecipeModel
import com.withapp.coffeememo.favorite.recipe.presentation.mapper.FavoriteRecipeModelMapper
import javax.inject.Inject

class FavoriteRecipePresenterImpl @Inject constructor()
    : FavoriteRecipePresenter {
    @Inject
    lateinit var mapper: FavoriteRecipeModelMapper

    override fun presentFavoriteRecipe(
        recipes: List<Recipe>
    ): List<FavoriteRecipeModel> {
        val result: MutableList<FavoriteRecipeModel> = mutableListOf()

        for (recipe in recipes) {
           result.add(mapper.execute(recipe))
        }

        return result
    }
}