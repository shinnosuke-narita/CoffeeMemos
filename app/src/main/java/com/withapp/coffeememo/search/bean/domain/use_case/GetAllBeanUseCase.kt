package com.withapp.coffeememo.search.bean.domain.use_case

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface GetAllBeanUseCase {
    suspend fun getAllBean(): List<SearchBeanModel>
}