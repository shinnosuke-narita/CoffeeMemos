package com.example.coffeememos.dao

import androidx.room.*
import com.example.coffeememos.entity.*
import com.example.coffeememos.home.bean.data.model.HomeBeanData
import com.example.coffeememos.search.bean.domain.model.SearchBeanModel
import kotlinx.coroutines.flow.Flow

@Dao
interface BeanDao {
    ///////////////////////
    /////  挿入系  /////////
    ///////////////////////
    @Insert
    suspend fun insert(bean: Bean)

    ///////////////////////
    /////  更新系  /////////
    ///////////////////////
    @Update
    suspend fun update(bean: Bean)

    @Query("UPDATE bean SET isFavorite = :favoriteFlag WHERE bean_id = :id;")
    suspend fun updateFavoriteByBeanId(id: Long, favoriteFlag: Boolean)

    ///////////////////////
    /////  削除系  /////////
    ///////////////////////
    @Query("DELETE FROM bean WHERE bean_id = :id;")
    suspend fun deleteBeanById(id: Long)

    @Query("DELETE FROM bean;")
    suspend fun clearTable()

    ///////////////////////
    /////  抽出系  /////////
    ///////////////////////
    @Query("SELECT * FROM bean ORDER BY bean_id DESC LIMIT 1;")
    suspend fun getNewestBean(): Bean

    @Transaction
    @Query("SELECT * FROM bean;")
    suspend fun getBeanWithRecipe(): List<RecipeWithBeans>

    @Query("SELECT * FROM bean;")
    fun getAllByFlow(): Flow<List<Bean>>

    @Query("SELECT * FROM bean;")
    suspend fun getAll(): List<Bean>

    @Query("SELECT * FROM bean WHERE bean_id = :beanId;")
    suspend fun getBeanById(beanId: Long): Bean

    @Query("SELECT * FROM bean JOIN recipe ON bean_id = recipe_bean_id;")
    fun getBeanAndRecipe(): Flow<Map<Bean, List<Recipe>>>

    @Query("SELECT bean_id, country, farm, district, store, process, species, rating, isFavorite, createdAt FROM bean;")
    fun getCustomBeanByFlow(): Flow<List<SearchBeanModel>>

    @Query("SELECT bean_id, country, farm, district, store, process, species, rating, isFavorite, createdAt FROM bean;")
    suspend fun getSearchBeanModel(): List<SearchBeanModel>

    @Query("SELECT bean_id, country, farm, district, rating, isFavorite, createdAt FROM bean;")
    suspend fun getHomeBeanData(): List<HomeBeanData>

    @Query("SELECT COUNT(*) FROM bean")
    fun getBeanCount(): Int

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT * FROM bean WHERE country LIKE '%' || :keyword || '%'" +
            "OR farm LIKE '%' || :keyword || '%'" +
            "OR district LIKE '%' || :keyword || '%'" +
            "OR store LIKE '%' || :keyword || '%'" +
            "OR species LIKE '%' || :keyword || '%'"
    )
    suspend fun getSearchBeanModelByKeyword(keyword: String): List<SearchBeanModel>









}