package com.withapp.coffeememo.domain.usecase.bean.getall

import com.withapp.coffeememo.domain.model.bean.SearchBeanModel

interface GetAllBeanUseCase {
    suspend fun getAllBean(): List<SearchBeanModel>
}