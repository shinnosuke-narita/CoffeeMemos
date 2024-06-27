package com.withapp.coffeememo.search.recipe.domain.model

enum class RecipeSortType(val index: Int) {
    NEW(0),
    OLD(1),
    RATING(2),
    ROAST(3),
    GRIND_SIZE(4),
    SOUR(5),
    BITTER(6),
    SWEET(7),
    FLAVOR(8),
    RICH(9);

    companion object {
        fun from(index: Int): RecipeSortType {
            return values().firstOrNull { it.index == index } ?: NEW
        }
    }
}