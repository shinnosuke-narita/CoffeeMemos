package com.withapp.coffeememo.favorite.recipe.domain.model

enum class RecipeSortType {
    NEW        { override fun getSortName(): String = "新しい順"},
    OLD        { override fun getSortName(): String = "古い順"},
    RATING     { override fun getSortName(): String = "評価順"},
    ROAST      { override fun getSortName(): String = "焙煎順"},
    GRIND_SIZE { override fun getSortName(): String = "挽き目順"};

    abstract fun getSortName(): String
}