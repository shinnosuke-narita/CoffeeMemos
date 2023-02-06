package com.example.coffeememos.di.data

import android.content.Context
import com.example.coffeememos.CoffeeMemosDatabase
import com.example.coffeememos.dao.BeanDao
import com.example.coffeememos.dao.RecipeDao
import com.example.coffeememos.dao.TasteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): CoffeeMemosDatabase {
        return CoffeeMemosDatabase.getDatabase(appContext)
    }

    @Provides
    fun provideRecipeDao(database: CoffeeMemosDatabase): RecipeDao {
        return database.recipeDao()
    }

    @Provides
    fun provideBeanDao(database: CoffeeMemosDatabase): BeanDao {
        return database.beanDao()
    }

    @Provides
    fun provideLogDao(database: CoffeeMemosDatabase): TasteDao {
        return database.tasteDao()
    }
}
