package com.withapp.coffeememo.domain.repository

import com.withapp.coffeememo.infra.data.entity.Taste
import kotlinx.coroutines.flow.Flow

interface TasteRepository {
    suspend fun insert(taste: Taste)

    suspend fun updateTaste(taste: Taste)

    fun getAll(): Flow<List<Taste>>

    suspend fun getTasteByRecipeId(recipeId: Long): Taste

    suspend fun getTasteIdByRecipeId(recipeId: Long): Long

    suspend fun getTasteById(id: Long): Taste
}