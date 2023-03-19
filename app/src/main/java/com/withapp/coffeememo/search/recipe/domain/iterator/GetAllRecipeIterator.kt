package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.presenter.SearchRecipePresenter
import com.withapp.coffeememo.search.recipe.domain.repository.SearchRecipeDiskRepository
import com.withapp.coffeememo.search.recipe.domain.use_case.GetAllRecipeUseCase
import javax.inject.Inject

class GetAllRecipeIterator @Inject constructor()
    : GetAllRecipeUseCase {
    @Inject
    lateinit var searchRecipeRepository: SearchRecipeDiskRepository
    @Inject
    lateinit var searchRecipePresenter: SearchRecipePresenter

    override suspend fun getAllRecipe(): List<SearchRecipeModel> {
       val recipes = searchRecipeRepository.getAllRecipe()
        return searchRecipePresenter.presentAllRecipes(recipes)
    }
}