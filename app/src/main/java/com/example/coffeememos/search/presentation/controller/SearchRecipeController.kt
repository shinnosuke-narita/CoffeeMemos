package com.example.coffeememos.search.presentation.controller

import com.example.coffeememos.search.SearchKeyWord
import com.example.coffeememos.search.SearchType
import com.example.coffeememos.search.domain.model.SearchRecipeModel
import com.example.coffeememos.search.domain.use_case.FreeWordSearchUseCase

class SearchRecipeController(
    private val freeWordSearchUseCase: FreeWordSearchUseCase
) {
   suspend fun freeWordSearch(freeWord: SearchKeyWord): List<SearchRecipeModel>? {
       if (freeWord.keyWord.isEmpty()) return null
       if (freeWord.type == SearchType.BEAN) return null

       return freeWordSearchUseCase.handle(freeWord.keyWord)
   }
}