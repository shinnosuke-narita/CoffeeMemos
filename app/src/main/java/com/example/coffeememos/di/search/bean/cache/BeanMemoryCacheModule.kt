package com.example.coffeememos.di.search.recipe.cache

import com.example.coffeememos.search.bean.data.cache.BeanMemoryCacheImpl
import com.example.coffeememos.search.bean.domain.cache.BeanMemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BeanMemoryCacheModule {

    @Provides
    @Singleton
    fun provideBeanMemoryCache(): BeanMemoryCache {
        return BeanMemoryCacheImpl
    }
}