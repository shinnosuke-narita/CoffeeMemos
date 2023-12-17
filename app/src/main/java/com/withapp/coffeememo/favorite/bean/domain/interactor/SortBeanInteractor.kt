package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.model.FavoriteBeanModel
import com.withapp.coffeememo.favorite.bean.domain.use_case.SortBeanUseCase
import javax.inject.Inject

class SortBeanInteractor @Inject constructor()
    : SortBeanUseCase{
    override fun handle(
        sortType: BeanSortType,
        list: List<FavoriteBeanModel>
    ): List<FavoriteBeanModel> {
        val result = when(sortType) {
            BeanSortType.OLD -> {
                list.sortedBy { bean -> bean.id}
            }
            BeanSortType.NEW -> {
                list.sortedByDescending { bean -> bean.id }
            }
            BeanSortType.RATING -> {
                list.sortedByDescending { bean -> bean.rating}
            }
        }

        return result
    }
}