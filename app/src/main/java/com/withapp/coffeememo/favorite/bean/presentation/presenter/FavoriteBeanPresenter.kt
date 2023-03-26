package com.withapp.coffeememo.favorite.bean.presentation.presenter

import com.withapp.coffeememo.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel

interface FavoriteBeanPresenter {
    fun presentFavoriteBeanModel(
        list: List<Bean>
    ): List<FavoriteBeanModel>
}