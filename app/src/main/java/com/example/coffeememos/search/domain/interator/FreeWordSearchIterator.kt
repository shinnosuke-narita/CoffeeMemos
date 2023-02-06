package com.example.coffeememos.search.domain.interator

import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.repository.SearchRecipeRepository
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import javax.inject.Inject

class FreeWordSearchIterator @Inject constructor()
    : FreeWordSearchUseCase {
    @Inject
    lateinit var freeWordSearchRepository: SearchRecipeRepository
    @Inject
    lateinit var freeWordSearchPresenter: SearchRecipePresenter

    override suspend fun handle(freeWord: String): List<SearchRecipeModel> {
        val originRecipeList: List<RecipeWithTaste> = freeWordSearchRepository.searchRecipeByFreeWord(freeWord)

        return freeWordSearchPresenter.presentFreeWordSearchRes(originRecipeList)
    }
}