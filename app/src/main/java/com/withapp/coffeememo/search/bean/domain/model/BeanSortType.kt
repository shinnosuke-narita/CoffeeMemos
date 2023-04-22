package com.withapp.coffeememo.search.bean.domain.model

enum class BeanSortType(val index: Int) {
    NEW(0),
    OLD(1),
    RATING(2);

    companion object {
        fun getSortTypeFormIndex(index: Int): BeanSortType {
            return values().firstOrNull { it.index == index} ?: NEW
        }
    }
}