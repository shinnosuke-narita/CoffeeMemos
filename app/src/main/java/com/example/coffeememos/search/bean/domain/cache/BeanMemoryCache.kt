package com.example.coffeememos.search.bean.domain.cache

interface BeanMemoryCache {
    fun setData(key: String, value: String)
    fun removeData(key: String)
    fun getData(key: String): String
}