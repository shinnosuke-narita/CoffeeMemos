package com.withapp.coffeememo.favorite.bean.presentation.controller

import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel

interface FavoriteBeanController {
    suspend fun getFavoriteBean(): List<FavoriteBeanModel>
}