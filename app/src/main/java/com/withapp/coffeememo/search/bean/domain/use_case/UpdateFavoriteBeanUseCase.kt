package com.withapp.coffeememo.search.bean.domain.use_case

interface UpdateFavoriteBeanUseCase {
    suspend fun handle(beanId: Long, isFavorite: Boolean)
}