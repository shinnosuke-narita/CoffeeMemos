package com.withapp.coffeememo.favorite.bean.domain.use_case

import com.withapp.coffeememo.favorite.bean.domain.model.BeanSortType
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput

interface GetSortDialogDataUseCase {
    fun handle(sortType: BeanSortType): SortDialogOutput
}