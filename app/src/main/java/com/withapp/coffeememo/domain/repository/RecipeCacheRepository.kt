package com.withapp.coffeememo.domain.repository

interface RecipeCacheRepository {
    fun setData(key: String, value: String)
    fun removeData(key: String): String?
    fun getData(key: String): String
}