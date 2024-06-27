package com.withapp.coffeememo.domain.usecase.bean.freewordsearch

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchBeanModel>
}