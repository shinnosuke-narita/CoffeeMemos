package com.withapp.coffeememo.presentation.favorite.bean.mapper

import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.domain.model.bean.FavoriteBeanModel

interface FavoriteBeanModelMapper {
    fun execute(bean: Bean): FavoriteBeanModel
}