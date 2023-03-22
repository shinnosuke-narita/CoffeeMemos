package com.withapp.coffeememo.favorite.recipe.domain.use_case

import com.withapp.coffeememo.entity.Recipe

interface GetFavoriteRecipeUseCase {
    suspend fun handle(): List<Recipe>
}