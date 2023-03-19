package com.withapp.coffeememo.search.recipe.domain.cache

interface RecipeMemoryCache {
    fun setData(key: String, value: String)
    fun removeData(key: String): String?
    fun getData(key: String): String
}