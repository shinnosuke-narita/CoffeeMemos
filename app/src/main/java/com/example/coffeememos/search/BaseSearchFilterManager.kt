package com.example.coffeememos.search

import com.example.coffeememos.CustomRecipe

abstract class BaseSearchFilterManager<T> {
    protected var filteredResult: MutableList<T> = mutableListOf()

    protected abstract fun filteringElementsCountIsZero(): Boolean

    protected abstract fun filter(currentSearchResult: List<T>)

    fun makeList(currentSearchResult: List<T>): List<T> {
        // フィルタリングする要素がない場合、そのまま現在のリストを返す
        if (filteringElementsCountIsZero()) return currentSearchResult

        filteredResult.clear()

        filter(currentSearchResult)

        return filteredResult
    }

    protected fun addItemIfPossible(
        recipe: T,
        recipeValue: String,
        filteringElements: List<String>,
        res: MutableList<T>) {
        for (filteringElement in filteringElements) {
            if (recipeValue.contains(filteringElement)) {
                res.add(recipe)
            }
        }
    }
    protected fun addItemIfPossible(
        recipe: T,
        recipeValue: Int,
        filteringElements: List<Int>,
        res: MutableList<T>) {
        for (filteringElement in filteringElements) {
            if (recipeValue == filteringElement) {
                res.add(recipe)
            }
        }
    }
}