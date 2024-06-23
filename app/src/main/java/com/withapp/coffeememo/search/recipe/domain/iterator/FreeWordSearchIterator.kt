package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.domain.repository.RecipeRepository
import com.withapp.coffeememo.search.recipe.data.mapper.SearchRecipeModelMapper
import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.presenter.SearchRecipePresenter
import com.withapp.coffeememo.search.recipe.domain.use_case.FreeWordSearchUseCase
import javax.inject.Inject

class FreeWordSearchIterator @Inject constructor(
    private val recipeRepo: RecipeRepository,
    private val mapper: SearchRecipeModelMapper,
    private val freeWordSearchPresenter: SearchRecipePresenter
) : FreeWordSearchUseCase {
    override suspend fun handle(freeWord: String): List<SearchRecipeModel> {
        val originRecipeList: List<SearchRecipeModel> =
            recipeRepo.getRecipeWithTasteByKeyword(freeWord).map(mapper::execute)

        return freeWordSearchPresenter.presentFreeWordSearchRes(originRecipeList)
    }
}