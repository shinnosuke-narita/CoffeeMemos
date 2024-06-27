package com.withapp.coffeememo.domain.usecase.bean.getsorttype

import com.withapp.coffeememo.search.bean.domain.model.BeanSortType
import javax.inject.Inject

class GetSortTypeInteractor @Inject constructor()
    : GetSortTypeUseCase {
    override fun handle(index: Int): BeanSortType {
        return BeanSortType.from(index)
    }
}