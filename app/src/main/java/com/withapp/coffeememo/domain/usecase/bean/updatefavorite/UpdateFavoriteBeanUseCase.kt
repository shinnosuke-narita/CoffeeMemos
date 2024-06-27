package com.withapp.coffeememo.domain.usecase.bean.updatefavorite

interface UpdateFavoriteBeanUseCase {
    suspend fun handle(beanId: Long, isFavorite: Boolean)
}