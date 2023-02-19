package com.example.coffeememos.search.domain.iterator

import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.repository.SearchRecipeDiskRepository
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase
import com.example.coffeememos.search.domain.model.SearchRecipeModel
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