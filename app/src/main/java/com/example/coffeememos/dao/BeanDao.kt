package com.example.coffeememos.dao

import androidx.room.*
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.CustomBean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.RecipeWithBeans
import kotlinx.coroutines.flow.Flow

@Dao
interface BeanDao {
    @Insert
    suspend fun insert(bean: Bean)

    @Update
    suspend fun update(bean: Bean)

    @Query("DELETE FROM bean WHERE bean_id = :id;")
    suspend fun deleteBeanById(id: Long)

    @Query("UPDATE bean SET isFavorite = :favoriteFlag WHERE bean_id = :id;")
    suspend fun updateFavoriteByBeanId(id: Long, favoriteFlag: Boolean)

    @Query("DELETE FROM bean;")
    suspend fun clearTable()

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
    fun getCustomBean(): Flow<List<CustomBean>>

    @Query("SELECT COUNT(*) FROM bean")
    fun getBeanCount(): Int
}