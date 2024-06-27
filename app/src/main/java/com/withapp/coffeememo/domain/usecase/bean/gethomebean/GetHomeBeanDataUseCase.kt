package com.withapp.coffeememo.domain.usecase.bean.gethomebean

import com.withapp.coffeememo.domain.model.bean.HomeBeanSource

interface GetHomeBeanDataUseCase {
    suspend fun handle(): HomeBeanSource
}