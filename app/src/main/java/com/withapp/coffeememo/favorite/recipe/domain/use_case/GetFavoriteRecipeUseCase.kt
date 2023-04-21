package com.withapp.coffeememo.favorite.recipe.domain.use_case

import com.withapp.coffeememo.core.data.entity.Recipe

interface GetFavoriteRecipeUseCase {
    suspend fun handle(): List<Recipe>
}