package com.example.coffeememos.home.bean.presentation.mapper

import com.example.coffeememos.home.bean.domain.model.HomeBeanModel
import com.example.coffeememos.home.bean.presentation.model.HomeBeanCardData

interface HomeBeanCardModelMapper {
    fun execute(homeBeanModel: HomeBeanModel): HomeBeanCardData
}