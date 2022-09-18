package com.example.coffeememos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.RecipeWithBeans

@Dao
interface BeanDao {
    @Insert
    suspend fun insert(bean: Bean)

    @Query("DELETE FROM bean")
    suspend fun clearTable()

    @Transaction
    @Query("SELECT * FROM bean")
    suspend fun getBeanWithRecipe(): List<RecipeWithBeans>

    @Query("SELECT * FROM bean")
    suspend fun getAll(): List<Bean>
}