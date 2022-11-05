package com.example.coffeememos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.coffeememos.entity.Bean
import com.example.coffeememos.entity.Recipe
import com.example.coffeememos.entity.RecipeWithTaste

@Dao
interface RecipeDao {
    @Insert
    suspend fun insert(recipe: Recipe)

    @Query("DELETE FROM recipe")
    suspend fun clearTable()

    @Query("SELECT * FROM recipe")
    suspend fun getAll(): List<Recipe>

    @Query("SELECT * FROM recipe ORDER BY recipe_id DESC LIMIT 1")
    suspend fun getNewestRecipe(): Recipe

    @Query("SELECT * FROM recipe WHERE recipe_id = :id")
    suspend fun getRecipeById(id: Long): Recipe

    @Query("UPDATE recipe SET isFavorite = :favoriteFlag WHERE recipe_id = :id")
    suspend fun updateFavoriteByRecipeId(id: Long, favoriteFlag: Boolean)

    @Transaction
    @Query("SELECT * FROM recipe")
    suspend fun getRecipeWithTaste(): List<RecipeWithTaste>
}