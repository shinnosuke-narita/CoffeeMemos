package com.withapp.coffeememo.home.bean.domain.repository

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanModel

interface StorageRepository {
    suspend fun getHomeBeanModel(): List<HomeBeanModel>
    suspend fun updateFavorite(beanId: Long, isFavorite: Boolean)
}