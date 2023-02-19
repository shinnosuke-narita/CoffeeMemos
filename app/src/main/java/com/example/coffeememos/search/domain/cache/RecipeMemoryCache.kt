package com.example.coffeememos.search.domain.cache

interface RecipeMemoryCache {
    fun setData(key: String, value: String)
    fun removeData(key: String)
    fun getData(key: String): String
}