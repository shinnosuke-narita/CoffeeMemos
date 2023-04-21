package com.withapp.coffeememo.home.bean.data.repository

import com.withapp.coffeememo.core.data.dao.BeanDao
import com.withapp.coffeememo.home.bean.data.mapper.HomeBeanModelMapper
import com.withapp.coffeememo.home.bean.data.model.HomeBeanData
import com.withapp.coffeememo.home.bean.domain.model.HomeBeanModel
import com.withapp.coffeememo.home.bean.domain.repository.StorageRepository
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