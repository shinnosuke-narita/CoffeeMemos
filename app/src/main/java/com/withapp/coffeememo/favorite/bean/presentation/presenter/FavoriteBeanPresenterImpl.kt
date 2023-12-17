package com.withapp.coffeememo.favorite.bean.presentation.presenter

import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.presentation.mapper.FavoriteBeanModelMapper
import javax.inject.Inject

class FavoriteBeanPresenterImpl @Inject constructor()
    : FavoriteBeanPresenter {
    @Inject
    lateinit var mapper: FavoriteBeanModelMapper

    override fun presentFavoriteBeanModel(
        list: List<Bean>
    ): List<FavoriteBeanModel> {
        return list.map { bean ->
            mapper.execute(bean)
        }
    }
}