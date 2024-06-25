package com.withapp.coffeememo.domain.usecase.bean

import com.withapp.coffeememo.domain.model.bean.FavoriteBeanModel

interface GetFavoriteBeanUseCase {
    suspend fun handle(): List<FavoriteBeanModel>
}