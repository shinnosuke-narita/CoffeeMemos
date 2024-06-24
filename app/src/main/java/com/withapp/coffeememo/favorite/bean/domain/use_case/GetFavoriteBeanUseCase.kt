package com.withapp.coffeememo.favorite.bean.domain.use_case

import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel

interface GetFavoriteBeanUseCase {
    suspend fun handle(): List<FavoriteBeanModel>
}