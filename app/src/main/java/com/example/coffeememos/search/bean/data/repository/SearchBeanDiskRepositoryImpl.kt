package com.example.coffeememos.search.bean.data.repository

import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel
import com.example.coffeememos.search.bean.domain.repository.SearchBeanDiskRepository
import javax.inject.Inject

class SearchBeanDiskRepositoryImpl @Inject constructor()
    : SearchBeanDiskRepository {
    @Inject
    lateinit var beanDao: BeanDao

    override suspend fun searchBeanByFreeWord(freeWord: String): List<SearchBeanModel> {
        return beanDao.getSearchBeanModelByKeyword(freeWord)
    }

    override suspend fun getAllBean(): List<SearchBeanModel> {
        return beanDao.getSearchBeanModel()
    }

    override suspend fun updateFavorite(beanId: Long, isFavorite: Boolean) {
        beanDao.updateFavoriteByBeanId(beanId, isFavorite)
    }
}