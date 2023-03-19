package com.withapp.coffeememo.di.search.recipe.cache

import com.withapp.coffeememo.search.bean.data.cache.BeanMemoryCacheImpl
import com.withapp.coffeememo.search.bean.domain.cache.BeanMemoryCache
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