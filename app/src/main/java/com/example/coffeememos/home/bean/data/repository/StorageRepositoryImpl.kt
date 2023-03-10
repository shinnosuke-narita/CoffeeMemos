package com.example.coffeememos.home.bean.data.repository

import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.home.bean.data.mapper.HomeBeanModelMapper
import com.example.coffeememos.home.bean.data.model.HomeBeanData
import com.example.coffeememos.home.bean.domain.model.HomeBeanModel
import com.example.coffeememos.home.bean.domain.repository.StorageRepository
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor()
    : StorageRepository {
    @Inject
    lateinit var beanDao: BeanDao
    @Inject
    lateinit var mapper: HomeBeanModelMapper

    override suspend fun getHomeBeanModel(): List<HomeBeanModel> {
        val beans: List<HomeBeanData> = beanDao.getHomeBeanData()
        return beans.map { bean ->
            mapper.execute(bean)
        }
    }

    override suspend fun updateFavorite(
        beanId: Long,
        isFavorite: Boolean) {
        beanDao.updateFavoriteByBeanId(beanId, isFavorite)
    }
}