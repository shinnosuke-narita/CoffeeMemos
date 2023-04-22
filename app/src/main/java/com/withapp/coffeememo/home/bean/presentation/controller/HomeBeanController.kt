package com.withapp.coffeememo.home.bean.presentation.controller

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanSource

interface HomeBeanController {
    suspend fun getHomeBeanData(): HomeBeanSource
    suspend fun updateBeanData(
        beanId: Long,
        isFavorite: Boolean
    ): HomeBeanSource
}