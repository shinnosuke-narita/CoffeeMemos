package com.withapp.coffeememo.home.bean.presentation.controller

import com.withapp.coffeememo.home.bean.presentation.model.HomeBeanOutput

interface HomeBeanController {
    suspend fun getHomeBeanData(): HomeBeanOutput
    suspend fun updateBeanData(
        beanId: Long,
        isFavorite: Boolean
    ): HomeBeanOutput
}