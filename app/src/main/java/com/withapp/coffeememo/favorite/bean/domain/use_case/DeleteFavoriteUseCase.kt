package com.withapp.coffeememo.favorite.bean.domain.use_case

interface DeleteFavoriteUseCase {
    suspend fun handle(beanId: Long)
}