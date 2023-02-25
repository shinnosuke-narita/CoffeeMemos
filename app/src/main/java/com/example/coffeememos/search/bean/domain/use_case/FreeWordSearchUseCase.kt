package com.example.coffeememos.search.bean.domain.use_case

import com.example.coffeememos.search.bean.domain.model.SearchBeanModel

interface FreeWordSearchUseCase {
    suspend fun handle(freeWord: String): List<SearchBeanModel>
}