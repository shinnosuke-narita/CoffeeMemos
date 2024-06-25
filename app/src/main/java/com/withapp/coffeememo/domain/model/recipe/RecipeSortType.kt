package com.withapp.coffeememo.domain.model.recipe

enum class RecipeSortType(val index: Int) {
    NEW(0),
    OLD(1),
    RATING(2),
    ROAST(3),
    GRIND_SIZE(4);

    companion object {
        fun getFromIndex(index: Int): RecipeSortType {
            return values().firstOrNull { sortType ->
                sortType.index == index
            } ?: NEW
        }
    }
}