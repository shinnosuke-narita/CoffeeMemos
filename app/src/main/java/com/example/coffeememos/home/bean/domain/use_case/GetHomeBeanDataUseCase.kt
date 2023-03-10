package com.example.coffeememos.home.bean.domain.use_case

import com.example.coffeememos.home.bean.domain.model.HomeBeanSource

interface GetHomeBeanDataUseCase {
    suspend fun handle(): HomeBeanSource
}