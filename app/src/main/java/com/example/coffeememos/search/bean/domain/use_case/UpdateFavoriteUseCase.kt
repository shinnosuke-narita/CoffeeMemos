package com.example.coffeememos.search.bean.domain.use_case

interface UpdateFavoriteUseCase {
    suspend fun handle(beanId: Long, isFavorite: Boolean)
}