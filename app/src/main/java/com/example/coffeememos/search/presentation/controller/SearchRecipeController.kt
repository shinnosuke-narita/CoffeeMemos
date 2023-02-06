package com.example.coffeememos.search.presentation.controller

import com.example.coffeememos.search.SearchKeyWord
import com.example.coffeememos.search.SearchType
import com.example.coffeememos.search.domain.model.RecipeSortType
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase
import com.example.coffeememos.search.domain.use_case.SortRecipeUseCase
import javax.inject.Inject

class SearchRecipeController @Inject constructor() {
    @Inject
    lateinit var freeWordSearchUseCase: FreeWordSearchUseCase
    @Inject
    lateinit var sortRecipeUseCase: SortRecipeUseCase

    suspend fun freeWordSearch(freeWord: SearchKeyWord): List<SearchRecipeModel>? {
        if (freeWord.keyWord.isEmpty()) return null
        if (freeWord.type == SearchType.BEAN) return null

        return freeWordSearchUseCase.handle(freeWord.keyWord)
    }

    fun sortRecipe(sortType: RecipeSortType, list: List<SearchRecipeModel>): List<SearchRecipeModel> {
        return sortRecipeUseCase.sort(sortType, list)
    }
}