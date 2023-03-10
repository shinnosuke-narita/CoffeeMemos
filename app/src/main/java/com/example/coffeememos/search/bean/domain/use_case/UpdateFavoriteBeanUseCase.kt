package com.example.coffeememos.search.bean.domain.use_case

interface UpdateFavoriteBeanUseCase {
    suspend fun handle(beanId: Long, isFavorite: Boolean)
}