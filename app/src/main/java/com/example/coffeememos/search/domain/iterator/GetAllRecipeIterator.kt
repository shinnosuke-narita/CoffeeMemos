package com.example.coffeememos.search.domain.iterator

import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.repository.SearchRecipeDiskRepository
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.search.domain.use_case.GetAllRecipeUseCase
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