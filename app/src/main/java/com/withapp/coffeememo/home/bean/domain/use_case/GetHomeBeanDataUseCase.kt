package com.withapp.coffeememo.home.bean.domain.use_case

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanSource

interface GetHomeBeanDataUseCase {
    suspend fun handle(): HomeBeanSource
}