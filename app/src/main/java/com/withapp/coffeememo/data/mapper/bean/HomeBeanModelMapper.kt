package com.withapp.coffeememo.data.mapper.bean

import com.withapp.coffeememo.data.model.bean.HomeBeanData
import com.withapp.coffeememo.domain.model.bean.HomeBeanModel

interface HomeBeanModelMapper {
    fun execute(bean: HomeBeanData): HomeBeanModel
}