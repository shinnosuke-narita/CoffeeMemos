package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.entity.Recipe
import com.withapp.coffeememo.favorite.recipe.domain.repository.StorageRepository
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetFavoriteRecipeUseCase
import javax.inject.Inject

class GetFavoriteRecipeInteractor @Inject constructor()
    : GetFavoriteRecipeUseCase {
    @Inject
    lateinit var repository: StorageRepository

    override suspend fun handle(): List<Recipe> {
        return repository.getFavoriteRecipe()
    }
}