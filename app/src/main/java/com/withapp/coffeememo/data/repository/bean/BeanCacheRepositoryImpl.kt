package com.withapp.coffeememo.data.repository.bean

import com.withapp.coffeememo.domain.repository.BeanCacheRepository

/**
 * シングルトン
 * メモリキャッシュクラス
 */
object BeanCacheRepositoryImpl : BeanCacheRepository {
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