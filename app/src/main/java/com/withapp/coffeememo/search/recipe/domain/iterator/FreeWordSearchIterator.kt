package com.withapp.coffeememo.search.recipe.domain.iterator

import com.withapp.coffeememo.search.recipe.domain.model.SearchRecipeModel
import com.withapp.coffeememo.search.recipe.domain.presenter.SearchRecipePresenter
import com.withapp.coffeememo.search.recipe.domain.repository.SearchRecipeDiskRepository
import com.withapp.coffeememo.search.recipe.domain.use_case.FreeWordSearchUseCase
import javax.inject.Inject

class FreeWordSearchIterator @Inject constructor()
    : FreeWordSearchUseCase {
    @Inject
    lateinit var freeWordSearchRepository: SearchRecipeDiskRepository
    @Inject
    lateinit var freeWordSearchPresenter: SearchRecipePresenter

    override suspend fun handle(freeWord: String): List<SearchRecipeModel> {
        val originRecipeList: List<SearchRecipeModel> = freeWordSearchRepository.searchRecipeByFreeWord(freeWord)

        return freeWordSearchPresenter.presentFreeWordSearchRes(originRecipeList)
    }
}