package com.example.coffeememos.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coffeememos.entity.Taste

@Dao
interface TasteDao {
    @Query("DELETE FROM taste")
    suspend fun clearTable()

    @Insert()
    suspend fun insert(taste: Taste)
}
