package com.withapp.coffeememo.di.search.recipe.cache

import com.withapp.coffeememo.search.recipe.data.cache.RecipeMemoryCacheImpl
import com.withapp.coffeememo.search.recipe.domain.cache.RecipeMemoryCache
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