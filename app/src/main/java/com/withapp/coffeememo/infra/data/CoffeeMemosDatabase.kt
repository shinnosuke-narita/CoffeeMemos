package com.withapp.coffeememo.infra.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.withapp.coffeememo.infra.data.dao.BeanDao
import com.withapp.coffeememo.infra.data.dao.RecipeDao
import com.withapp.coffeememo.infra.data.dao.TasteDao
import com.withapp.coffeememo.infra.data.entity.Bean
import com.withapp.coffeememo.infra.data.entity.Recipe
import com.withapp.coffeememo.infra.data.entity.Taste

@Database(entities = [Recipe::class, Bean::class, Taste::class], version = 12)
abstract class CoffeeMemosDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun beanDao(): BeanDao
    abstract fun tasteDao(): TasteDao

    companion object {
        @Volatile
        private var INSTANCE: CoffeeMemosDatabase? = null

        fun getDatabase(
            context: Context
        ) : CoffeeMemosDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CoffeeMemosDatabase::class.java,
                    "coffee_memo_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return@synchronized instance
            }
        }
    }
}