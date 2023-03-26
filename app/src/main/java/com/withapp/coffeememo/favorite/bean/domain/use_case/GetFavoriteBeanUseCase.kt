package com.withapp.coffeememo.favorite.bean.domain.use_case

import com.withapp.coffeememo.entity.Bean

interface GetFavoriteBeanUseCase {
    suspend fun handle(): List<Bean>
}