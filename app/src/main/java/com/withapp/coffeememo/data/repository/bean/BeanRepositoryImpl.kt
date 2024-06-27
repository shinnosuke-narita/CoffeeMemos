package com.withapp.coffeememo.data.repository.bean

import com.withapp.coffeememo.core.data.dao.BeanDao
import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.RecipeWithBeans
import com.withapp.coffeememo.domain.repository.BeanRepository
import com.withapp.coffeememo.data.model.bean.HomeBeanData
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BeanRepositoryImpl @Inject constructor(
    private val beanDao: BeanDao
) : BeanRepository {
    override suspend fun insert(bean: Bean) {
        beanDao.insert(bean)
    }

    override suspend fun update(bean: Bean) {
        beanDao.update(bean)
    }

    override suspend fun updateFavoriteByBeanId(id: Long, favoriteFlag: Boolean) {
        beanDao.updateFavoriteByBeanId(id, favoriteFlag)
    }

    override suspend fun deleteFavoriteBean(id: Long) {
        beanDao.deleteFavoriteBean(id)
    }

    override suspend fun deleteBeanById(id: Long) {
        beanDao.deleteBeanById(id)
    }

    override suspend fun getNewestBean(): Bean {
        return beanDao.getNewestBean()
    }

    override suspend fun getBeanWithRecipe(): List<RecipeWithBeans> {
        return beanDao.getBeanWithRecipe()
    }

    override fun getAllByFlow(): Flow<List<Bean>> {
        return beanDao.getAllByFlow()
    }

    override suspend fun getBeanById(beanId: Long): Bean {
        return beanDao.getBeanById(beanId)
    }

    override fun getBeanAndRecipe(): Flow<Map<Bean, List<Recipe>>> {
        return beanDao.getBeanAndRecipe()
    }

    override fun getCustomBeanByFlow(): Flow<List<SearchBeanModel>> {
        return beanDao.getCustomBeanByFlow()
    }

    override suspend fun getSearchBeanModel(): List<SearchBeanModel> {
        return beanDao.getSearchBeanModel()
    }

    override suspend fun getHomeBeanData(): List<HomeBeanData> {
        return beanDao.getHomeBeanData()
    }

    override fun getBeanCount(): Int {
        return beanDao.getBeanCount()
    }

    override suspend fun getFavoriteBean(): List<Bean> {
        return beanDao.getFavoriteBean()
    }

    override suspend fun getSearchBeanModelByKeyword(keyword: String): List<SearchBeanModel> {
        return beanDao.getSearchBeanModelByKeyword(keyword)
    }
}