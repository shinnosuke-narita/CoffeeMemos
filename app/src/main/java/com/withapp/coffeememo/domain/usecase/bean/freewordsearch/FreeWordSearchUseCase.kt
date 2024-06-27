package com.withapp.coffeememo.domain.usecase.bean.freewordsearch

import com.withapp.coffeememo.domain.model.bean.SearchBeanModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchBeanModel>
}