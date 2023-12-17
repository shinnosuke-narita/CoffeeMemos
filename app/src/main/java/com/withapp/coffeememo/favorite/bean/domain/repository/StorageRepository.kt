package com.withapp.coffeememo.favorite.bean.domain.repository

import com.withapp.coffeememo.core.data.entity.Bean

interface StorageRepository {
    suspend fun getFavoriteBeans(): List<Bean>
    suspend fun deleteFavoriteBean(id: Long)
}