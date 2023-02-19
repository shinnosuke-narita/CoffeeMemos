package com.example.coffeememos.di.search.cache

import com.example.coffeememos.search.data.cache.RecipeMemoryCacheImpl
import com.example.coffeememos.search.domain.cache.RecipeMemoryCache
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeMemoryCacheModule {

//    @Binds
//    @Singleton
//    abstract fun bindRecipeMemoryCache(
//        recipeMemoryCacheImpl: RecipeMemoryCacheImpl
//    ): RecipeMemoryCache

    @Provides
    @Singleton
    fun provideRecipeMemoryCache(): RecipeMemoryCache {
        return RecipeMemoryCacheImpl
    }
}