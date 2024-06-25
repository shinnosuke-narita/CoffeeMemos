package com.withapp.coffeememo.home.bean.data.mapper

import com.withapp.coffeememo.home.bean.data.model.HomeBeanData
import com.withapp.coffeememo.domain.model.bean.HomeBeanModel

interface HomeBeanModelMapper {
    fun execute(bean: HomeBeanData): HomeBeanModel
}