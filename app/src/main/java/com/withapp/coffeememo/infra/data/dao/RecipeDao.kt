package com.withapp.coffeememo.infra.data.dao

import androidx.room.*
import com.withapp.coffeememo.infra.data.entity.Recipe
import com.withapp.coffeememo.infra.data.entity.RecipeWithTaste
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    ///////////////////////
    /////  挿入系  /////////
    ///////////////////////
    @Insert
    suspend fun insert(recipe: Recipe)

    @Insert
    suspend fun insertAll(recipes: List<Recipe>)

    ///////////////////////
    /////  更新系  /////////
    ///////////////////////
    @Update
    suspend fun update(recipe: Recipe)

    @Query("UPDATE recipe SET isFavorite = :favoriteFlag" +
            " WHERE recipe_id = :id;")
    suspend fun updateFavoriteByRecipeId(
        id: Long,
        favoriteFlag: Boolean)

    @Query("UPDATE recipe SET country = :country" +
            " WHERE recipe_id = :id;")
    suspend fun updateCountryById(
        id: Long,
        country: String)

    ///////////////////////
    /////  削除系  /////////
    ///////////////////////
    @Query("DELETE FROM recipe")
    suspend fun clearTable()

    @Query("DELETE FROM recipe WHERE recipe_id = :id")
    suspend fun deleteById(id: Long)

    ///////////////////////
    /////  抽出系  /////////
    ///////////////////////
    @Query("SELECT recipe_id FROM recipe" +
            " ORDER BY recipe_id DESC LIMIT 1;")
    suspend fun getNewestRecipeId(): Long

    @Query("SELECT * FROM recipe WHERE recipe_id = :id;")
    suspend fun getRecipeById(id: Long): Recipe

    @Query("SELECT recipe_id FROM recipe WHERE recipe_bean_id = :beanId;")
    suspend fun getRecipeIdsByBeanId(beanId: Long): List<Long>

    @Query("SELECT COUNT(*) FROM recipe;")
    fun getTotalCount(): Int

    @Transaction
    @Query("SELECT * FROM recipe;")
    fun getRecipeWithTasteByFlow(): Flow<List<RecipeWithTaste>>

    @Transaction
    @Query("SELECT * FROM recipe;")
    suspend fun getRecipeWithTaste(): List<RecipeWithTaste>

    @Transaction
    @Query("SELECT * FROM recipe" +
            " WHERE country" +
            " LIKE '%' || :keyword || '%'" +
            " OR tool LIKE '%' || :keyword || '%';"
    )
    suspend fun getRecipeWithTasteByKeyword(
        keyword: String
    ): List<RecipeWithTaste>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1;")
    suspend fun getFavoriteRecipe(): List<Recipe>
}