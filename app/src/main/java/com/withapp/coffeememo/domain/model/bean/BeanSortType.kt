package com.withapp.coffeememo.domain.model.bean

enum class BeanSortType(val index: Int) {
    NEW(0),
    OLD(1),
    RATING(2);

    companion object {
        fun from(index: Int): BeanSortType {
            return values().firstOrNull { it.index == index} ?: NEW
        }
    }
}