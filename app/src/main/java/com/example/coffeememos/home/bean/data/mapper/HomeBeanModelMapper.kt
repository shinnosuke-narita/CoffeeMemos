package com.example.coffeememos.home.bean.data.mapper

import com.example.coffeememos.home.bean.data.model.HomeBeanData
import com.example.coffeememos.home.bean.domain.model.HomeBeanModel

interface HomeBeanModelMapper {
    fun execute(bean: HomeBeanData): HomeBeanModel
}