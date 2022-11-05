package com.example.coffeememos.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.RecipeWithBeans
import kotlinx.coroutines.flow.Flow

@Dao
interface BeanDao {
    @Insert
    suspend fun insert(bean: Bean)

    @Update
    suspend fun update(bean: Bean)

    @Query("UPDATE bean SET isFavorite = :favoriteFlag WHERE bean_id = :id")
    suspend fun updateFavoriteByBeanId(id: Long, favoriteFlag: Boolean)

    @Query("DELETE FROM bean")
    suspend fun clearTable()

    @Query("SELECT * FROM bean ORDER BY bean_id DESC LIMIT 1")
    suspend fun getNewestBean(): Bean

    @Transaction
    @Query("SELECT * FROM bean")
    suspend fun getBeanWithRecipe(): List<RecipeWithBeans>

    @Query("SELECT * FROM bean")
    suspend fun getAll(): List<Bean>

    @Query("SELECT * FROM bean WHERE bean_id = :beanId")
    suspend fun getBeanById(beanId: Long): Bean

    @Query("SELECT * FROM bean JOIN recipe ON bean_id = recipe_bean_id")
    suspend fun getBeanAndRecipe(): Map<Bean, List<Recipe>>


}