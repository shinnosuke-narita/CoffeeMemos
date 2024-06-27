package com.withapp.coffeememo.domain.repository

interface BeanCacheRepository {
    fun setData(key: String, value: String)
    fun removeData(key: String)
    fun getData(key: String): String
}