package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetSortTypeUseCase
import javax.inject.Inject

class GetSortTypeInteractor @Inject constructor()
    : GetSortTypeUseCase {
    override fun handle(index: Int): BeanSortType {
        return BeanSortType.getFromIndex(index)
    }
}