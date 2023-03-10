package com.example.coffeememos.home.bean.presentation.controller

import com.example.coffeememos.home.bean.presentation.model.HomeBeanOutput

interface HomeBeanController {
    suspend fun getHomeBeanData(): HomeBeanOutput
    suspend fun updateBeanData(
        beanId: Long,
        isFavorite: Boolean
    ): HomeBeanOutput
}