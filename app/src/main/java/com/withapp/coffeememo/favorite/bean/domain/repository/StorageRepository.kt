package com.withapp.coffeememo.favorite.bean.domain.repository

import com.withapp.coffeememo.entity.Bean

interface StorageRepository {
    suspend fun getFavoriteBeans(): List<Bean>
}