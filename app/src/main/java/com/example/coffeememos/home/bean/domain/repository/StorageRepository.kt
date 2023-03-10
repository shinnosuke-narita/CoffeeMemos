package com.example.coffeememos.home.bean.domain.repository

import com.example.coffeememos.home.bean.domain.model.HomeBeanModel

interface StorageRepository {
    suspend fun getHomeBeanModel(): List<HomeBeanModel>
    suspend fun updateFavorite(beanId: Long, isFavorite: Boolean)
}