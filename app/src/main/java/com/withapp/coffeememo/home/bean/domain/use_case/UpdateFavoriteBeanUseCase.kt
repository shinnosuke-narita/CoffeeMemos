package com.withapp.coffeememo.home.bean.domain.use_case

interface UpdateFavoriteBeanUseCase {
    suspend fun handle(beanId: Long, isFavorite: Boolean)
}