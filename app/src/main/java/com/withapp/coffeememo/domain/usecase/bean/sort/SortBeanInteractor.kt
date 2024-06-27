package com.withapp.coffeememo.domain.usecase.bean.sort

import com.withapp.coffeememo.search.bean.domain.model.BeanSortType
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import javax.inject.Inject

class SortBeanInteractor @Inject constructor() : SortBeanUseCase {
    override fun sort(
        sortType: BeanSortType,
        list: List<SearchBeanModel>
    ): List<SearchBeanModel> {

        val result = when(sortType) {
            BeanSortType.OLD -> list.sortedBy { bean -> bean.id}
            BeanSortType.NEW -> list.sortedByDescending { bean -> bean.id }
            BeanSortType.RATING -> list.sortedByDescending { bean -> bean.rating }
        }

        return result
    }
}