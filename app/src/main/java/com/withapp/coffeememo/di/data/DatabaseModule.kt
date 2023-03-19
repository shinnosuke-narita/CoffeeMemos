package com.withapp.coffeememo.di.data

import android.content.Context
import com.withapp.coffeememo.CoffeeMemosDatabase
import com.withapp.coffeememo.dao.BeanDao
import com.withapp.coffeememo.dao.RecipeDao
import com.withapp.coffeememo.dao.TasteDao
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
