package com.withapp.coffeememo.domain.usecase.bean

interface UpdateFavoriteBeanUseCase {
    suspend fun handle(beanId: Long, isFavorite: Boolean)
}