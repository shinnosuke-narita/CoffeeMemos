package com.withapp.coffeememo.di.favorite.bean.repository


import com.withapp.coffeememo.favorite.bean.data.repository.StorageRepositoryImpl
import com.withapp.coffeememo.favorite.bean.domain.repository.StorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class StorageRepositoryModule {

    @Binds
    abstract fun bindStorageRepository(
        storageRepositoryImpl: StorageRepositoryImpl
    ): StorageRepository
}