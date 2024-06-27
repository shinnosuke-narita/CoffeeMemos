package com.withapp.coffeememo.di.search.recipe.cache

import com.withapp.coffeememo.search.recipe.data.cache.RecipeCacheRepositoryImpl
import com.withapp.coffeememo.domain.repository.RecipeCacheRepository
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
    fun provideRecipeMemoryCache(): RecipeCacheRepository {
        return RecipeCacheRepositoryImpl
    }
}