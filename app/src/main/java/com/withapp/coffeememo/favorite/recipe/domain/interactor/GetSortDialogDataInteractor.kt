package com.withapp.coffeememo.favorite.recipe.domain.interactor

import com.withapp.coffeememo.favorite.recipe.domain.model.RecipeSortType
import com.withapp.coffeememo.favorite.recipe.domain.model.SortDialogOutput
import com.withapp.coffeememo.favorite.recipe.domain.use_case.GetSortDialogDataUseCase
import javax.inject.Inject

class GetSortDialogDataInteractor @Inject constructor()
    : GetSortDialogDataUseCase  {

    override fun handle(sortType: RecipeSortType): SortDialogOutput {
        val sortTypeValues: Array<RecipeSortType> =
            RecipeSortType.values()

        // sort名のリスト
        val sortTypeStrList: List<String> =
            sortTypeValues.map { type ->
                return@map type.getSortName()
            }
        // 現在のソートのインデックス
        val currentIndex: Int = getCurrentIndex(sortType, sortTypeValues)

        return SortDialogOutput(
            currentIndex,
            sortTypeStrList
        )
    }

    private fun getCurrentIndex(
        currentSortType: RecipeSortType,
        list: Array<RecipeSortType>
    ): Int {
        val currentSortName: String = currentSortType.getSortName()

        for ((index, type) in list.withIndex()) {
            if (currentSortName == type.getSortName()) {
                return index
            }
        }

        return 0
    }
}