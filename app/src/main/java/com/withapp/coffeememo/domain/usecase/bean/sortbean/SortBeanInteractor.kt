package com.withapp.coffeememo.domain.usecase.bean.sortbean

import com.withapp.coffeememo.domain.model.bean.FavoriteBeanModel
import com.withapp.coffeememo.search.bean.domain.model.BeanSortType
import javax.inject.Inject

class SortBeanInteractor @Inject constructor()
    : SortBeanUseCase {
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