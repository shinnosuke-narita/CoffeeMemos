package com.withapp.coffeememo.search.bean.domain.use_case

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchBeanModel>
}