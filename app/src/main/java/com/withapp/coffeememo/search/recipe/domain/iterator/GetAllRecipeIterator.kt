package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.search.recipe.data.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.presenter.SearchRecipePresenter
import com.withapp.coffeememo.search.recipe.domain.use_case.GetAllRecipeUseCase
import javax.inject.Inject

class GetAllRecipeIterator @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: SearchRecipeModelMapper,
    private val searchRecipePresenter: SearchRecipePresenter
) : GetAllRecipeUseCase {
    override suspend fun getAllRecipe(): List<SearchRecipeModel> {
       val recipes = recipeRepo.getRecipeWithTaste().map(mapper::execute)
        return searchRecipePresenter.presentAllRecipes(recipes)
    }
}