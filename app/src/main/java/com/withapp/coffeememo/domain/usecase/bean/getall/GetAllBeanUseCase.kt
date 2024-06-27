package com.withapp.coffeememo.domain.usecase.bean.getall

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface GetAllBeanUseCase {
    suspend fun getAllBean(): List<SearchBeanModel>
}