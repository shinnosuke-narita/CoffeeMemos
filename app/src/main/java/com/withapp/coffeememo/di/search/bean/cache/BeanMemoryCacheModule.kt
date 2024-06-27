package com.withapp.coffeememo.di.search.recipe.cache

import com.withapp.coffeememo.data.repository.bean.BeanCacheRepositoryImpl
import com.withapp.coffeememo.domain.repository.BeanCacheRepository
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
    fun provideBeanMemoryCache(): BeanCacheRepository {
        return BeanCacheRepositoryImpl
    }
}