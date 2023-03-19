package com.withapp.coffeememo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.withapp.coffeememo.dao.BeanDao
import com.withapp.coffeememo.dao.RecipeDao
import com.withapp.coffeememo.dao.TasteDao
import com.withapp.coffeememo.entity.*

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