package com.example.coffeememos.search.domain.interator

import com.example.coffeememos.entity.RecipeWithTaste
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.repository.SearchRecipeRepository
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase
import com.example.coffeememos.search.domain.model.SearchRecipeModel

class FreeWordSearchIterator(
    private val freeWordSearchRepository: SearchRecipeRepository,
    private val freeWordSearchPresenter: SearchRecipePresenter
) : FreeWordSearchUseCase {
    override suspend fun handle(freeWord: String): List<SearchRecipeModel> {
        val originRecipeList: List<RecipeWithTaste> = freeWordSearchRepository.searchRecipeByFreeWord(freeWord)

        return freeWordSearchPresenter.presentFreeWordSearchRes(originRecipeList)
    }
}