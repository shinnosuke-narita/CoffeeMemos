package com.withapp.coffeememo.domain.usecase.bean

interface DeleteFavoriteUseCase {
    suspend fun handle(beanId: Long)
}