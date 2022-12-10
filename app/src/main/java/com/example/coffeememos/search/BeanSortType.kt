package com.example.coffeememos.search

enum class BeanSortType {
    NEW    { override fun getSortName(): String = "新しい順" },
    OLD    { override fun getSortName(): String = "古い順" },
    RATING { override fun getSortName(): String = "評価順" };

    abstract fun getSortName(): String
}