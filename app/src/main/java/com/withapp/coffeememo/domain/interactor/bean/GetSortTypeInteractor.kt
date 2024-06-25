package com.withapp.coffeememo.domain.interactor.bean

import com.withapp.coffeememo.domain.model.bean.BeanSortType
import com.withapp.coffeememo.domain.usecase.bean.GetSortTypeUseCase
import javax.inject.Inject

class GetSortTypeInteractor @Inject constructor()
    : GetSortTypeUseCase {
    override fun handle(index: Int): BeanSortType {
        return BeanSortType.getFromIndex(index)
    }
}