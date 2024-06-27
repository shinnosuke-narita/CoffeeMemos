package com.withapp.coffeememo.domain.usecase.recipe.getall

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.search.recipe.data.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import javax.inject.Inject

class GetAllRecipeInteractor @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: SearchRecipeModelMapper
) : GetAllRecipeUseCase {
    override suspend fun getAllRecipe(): List<SearchRecipeModel> {
        return recipeRepo.getRecipeWithTaste().map(mapper::execute)
    }
}