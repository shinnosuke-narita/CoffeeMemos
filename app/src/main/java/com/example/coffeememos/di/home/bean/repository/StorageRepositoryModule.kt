package com.example.coffeememos.di.home.bean.repository

import com.example.coffeememos.home.bean.data.repository.StorageRepositoryImpl
import com.example.coffeememos.home.bean.domain.repository.StorageRepository
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