package com.example.coffeememos.di.search.cache

import com.example.coffeememos.search.recipe.data.cache.RecipeMemoryCacheImpl
import com.example.coffeememos.search.recipe.domain.cache.RecipeMemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeMemoryCacheModule {

    @Provides
    @Singleton
    fun provideRecipeMemoryCache(): RecipeMemoryCache {
        return RecipeMemoryCacheImpl
    }
}