package com.withapp.coffeememo.favorite.bean.presentation.mapper

import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel

interface FavoriteBeanModelMapper {
    fun execute(bean: Bean): FavoriteBeanModel
}