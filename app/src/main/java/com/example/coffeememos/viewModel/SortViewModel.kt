package com.example.coffeememos.viewModel

import androidx.lifecycle.ViewModel
import com.example.coffeememos.search.SortItem
import com.example.coffeememos.search.SortType

class SortViewModel : ViewModel() {
    // listview data
    lateinit var sortItemList: List<SortItem>

    fun setSortItemList(index: Int) {
        sortItemList = makeSortData(index)
    }

    private fun makeSortData(index: Int): List<SortItem> {
        val result: MutableList<SortItem> = mutableListOf()

        for ((i, sortType) in SortType.values().withIndex()) {
            val isSelected: Boolean = i == index
            result.add(SortItem(sortType, isSelected))
        }
        return result
    }

    fun changeData(index: Int) {
        for ((i, item) in sortItemList.withIndex()) {
            item.isSelected = i == index
        }
    }

    fun getSelectedSortType(): SortType {
        for (sortItem in sortItemList) {
            if (sortItem.isSelected) {
                return sortItem.type
            }
        }
        // 見つからなかったとき(デフォルト値のようなもの)
        return SortType.NEW
    }
}