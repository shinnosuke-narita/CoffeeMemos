package com.example.coffeememos.search

abstract class BaseSearchFilterManager<T> {
    private val filteredResult: MutableList<T> = mutableListOf()

    protected abstract fun filteringElementsCountIsZero(): Boolean

    protected abstract fun filter(currentSearchResult: List<T>)

    fun makeList(currentSearchResult: List<T>): List<T> {
        // フィルタリングする要素がない場合、そのまま現在のリストを返す
        if (filteringElementsCountIsZero()) return currentSearchResult

        filteredResult.clear()

        filter(currentSearchResult)

        return filteredResult
    }


    // 追加したら、trueを返す
    protected fun addItemIfPassCheck(item: T, itemValue: String, selectedValues: List<String>): Boolean {
        if (selectedValues.isEmpty()) return false

        for (selectedValue in selectedValues) {
            if (itemValue.contains(selectedValue)) {
                filteredResult.add(item)
                return true
            }
        }
        return false
    }

    // 追加したら、trueを返す
    protected fun addItemIfPassCheck(item: T, itemValue: Int, selectedValues: List<Int>): Boolean {
        if (selectedValues.isEmpty()) return false

        for (selectedValue in selectedValues) {
            if (itemValue == selectedValue) {
                filteredResult.add(item)
                return true
            }
        }
        return false
    }
}