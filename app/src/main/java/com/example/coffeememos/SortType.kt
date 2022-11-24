package com.example.coffeememos

enum class SortType {
    NEW        { override fun getSortName(): String = "新しい順"},
    OLD        { override fun getSortName(): String = "古い順"},
    RATING     { override fun getSortName(): String = "評価順"},
    ROAST      { override fun getSortName(): String = "焙煎順"},
    GRIND_SIZE { override fun getSortName(): String = "挽き目順"},
    SOUR       { override fun getSortName(): String = "酸味順"},
    BITTER     { override fun getSortName(): String = "苦味順"},
    SWEET      { override fun getSortName(): String = "甘味順"},
    FLAVOR     { override fun getSortName(): String = "香り順"},
    RICH       { override fun getSortName(): String = "コク順"};

    abstract fun getSortName(): String
}