package com.withapp.coffeememo.search.bean.domain.repository

import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel

interface SearchBeanDiskRepository {
    /**
     *  キーワード検索
     */
    suspend fun searchBeanByFreeWord(freeWord: String): List<SearchBeanModel>
    /**
     * 全件取得
     */
    suspend fun getAllBean(): List<SearchBeanModel>
    /**
     * お気に入り更新
     */
    suspend fun updateFavorite(beanId: Long, isFavorite: Boolean)
}