package com.withapp.coffeememo.search.bean.data.repository

import com.withapp.coffeememo.dao.BeanDao
import com.withapp.coffeememo.search.bean.domain.model.SearchBeanModel
import com.withapp.coffeememo.search.bean.domain.repository.SearchBeanDiskRepository
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