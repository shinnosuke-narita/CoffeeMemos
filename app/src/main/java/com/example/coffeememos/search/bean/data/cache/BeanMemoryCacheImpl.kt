package com.example.coffeememos.search.bean.data.cache

import com.example.coffeememos.search.bean.domain.cache.BeanMemoryCache

/**
 * シングルトン
 * メモリキャッシュクラス
 */
object BeanMemoryCacheImpl : BeanMemoryCache {
    private val cacheData: MutableMap<String, String> = mutableMapOf()

    override fun setData(key: String, value: String) {
        if (key.isEmpty()) return

        cacheData[key] = value
    }

    override fun removeData(key: String) {
        if (key.isEmpty()) return

        cacheData.remove(key)
    }

    override fun getData(key: String): String {
        if (key.isEmpty()) return ""

        return cacheData[key] ?: ""
    }
}