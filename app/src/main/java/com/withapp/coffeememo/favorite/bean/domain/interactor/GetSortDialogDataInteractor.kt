package com.withapp.coffeememo.favorite.bean.domain.interactor

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.bean.domain.use_case.GetSortDialogDataUseCase
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput
import javax.inject.Inject

class GetSortDialogDataInteractor @Inject constructor()
    : GetSortDialogDataUseCase {
    override fun handle(sortType: BeanSortType): SortDialogOutput {
        val currentIndex: Int =
            BeanSortType.getIndexByName(sortType.getSortName())
        val sortNameList: List<String> = BeanSortType.getNameList()

        return SortDialogOutput(
            currentIndex,
            sortNameList
        )
    }
}