package com.example.coffeememos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.coffeememos.entity.Taste

@Dao
interface TasteDao {
    @Query("DELETE FROM taste")
    suspend fun clearTable()

    @Insert()
    suspend fun insert(taste: Taste)

    @Update()
    suspend fun updateTaste(taste: Taste)

    @Query("SELECT * FROM taste")
    suspend fun getAll(): List<Taste>

    @Query("SELECT * FROM taste WHERE taste_recipe_id = :recipeId")
    suspend fun getTasteByRecipeId(recipeId: Long): Taste

    @Query("SELECT id FROM taste WHERE taste_recipe_id = :recipeId ")
    suspend fun getTasteIdByRecipeId(recipeId: Long): Long
}
