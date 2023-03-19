package com.withapp.coffeememo.home.bean.presentation.mapper

import com.withapp.coffeememo.home.bean.domain.model.HomeBeanModel
import com.withapp.coffeememo.home.bean.presentation.model.HomeBeanCardData

interface HomeBeanCardModelMapper {
    fun execute(homeBeanModel: HomeBeanModel): HomeBeanCardData
}