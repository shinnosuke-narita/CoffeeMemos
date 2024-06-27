package com.withapp.coffeememo.domain.usecase.bean.deletefavorite

interface DeleteFavoriteUseCase {
    suspend fun handle(beanId: Long)
}