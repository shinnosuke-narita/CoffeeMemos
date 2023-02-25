package com.example.coffeememos.search.bean.domain.use_case

import com.example.coffeememos.search.bean.domain.model.SearchBeanModel

interface GetAllBeanUseCase {
    suspend fun getAllBean(): List<SearchBeanModel>
}