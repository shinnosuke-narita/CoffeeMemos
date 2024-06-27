package com.withapp.coffeememo.domain.repository

import com.withapp.coffeememo.core.data.entity.Bean
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.RecipeWithBeans
import com.withapp.coffeememo.data.model.bean.HomeBeanData
import com.withapp.coffeememo.domain.model.bean.SearchBeanModel
import kotlinx.coroutines.flow.Flow

interface BeanRepository {
    suspend fun insert(bean: Bean)

    suspend fun update(bean: Bean)

    suspend fun updateFavoriteByBeanId(id: Long, favoriteFlag: Boolean)

    suspend fun deleteFavoriteBean(id: Long)

    suspend fun deleteBeanById(id: Long)

    suspend fun getNewestBean(): Bean

    suspend fun getBeanWithRecipe(): List<RecipeWithBeans>

    fun getAllByFlow(): Flow<List<Bean>>

    suspend fun getBeanById(beanId: Long): Bean

    fun getBeanAndRecipe(): Flow<Map<Bean, List<Recipe>>>

    fun getCustomBeanByFlow(): Flow<List<SearchBeanModel>>

    suspend fun getSearchBeanModel(): List<SearchBeanModel>

    suspend fun getHomeBeanData(): List<HomeBeanData>

    fun getBeanCount(): Int

    suspend fun getFavoriteBean(): List<Bean>

    suspend fun getSearchBeanModelByKeyword(keyword: String): List<SearchBeanModel>
}