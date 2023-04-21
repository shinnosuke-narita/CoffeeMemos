package com.withapp.coffeememo.core.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.withapp.coffeememo.core.data.entity.Recipe
import com.withapp.coffeememo.core.data.entity.Taste
import kotlinx.coroutines.flow.Flow

@Dao
interface TasteDao {
    @Query("DELETE FROM taste")
    suspend fun clearTable()

    @Insert()
    suspend fun insert(taste: Taste)

    @Update()
    suspend fun updateTaste(taste: Taste)

    @Query("SELECT * FROM taste")
    fun getAll(): Flow<List<Taste>>

    @Query("SELECT * FROM taste WHERE taste_recipe_id = :recipeId")
    suspend fun getTasteByRecipeId(recipeId: Long): Taste

    @Query("SELECT id FROM taste WHERE taste_recipe_id = :recipeId ")
    suspend fun getTasteIdByRecipeId(recipeId: Long): Long

    @Query("SELECT * FROM taste WHERE id = :id")
    suspend fun getTasteById(id: Long): Taste
}
