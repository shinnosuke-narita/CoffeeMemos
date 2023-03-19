package com.withapp.coffeememo.di.create.recipe.repository

import com.withapp.coffeememo.create.recipe.data.repository.StorageRepositoryImpl
import com.withapp.coffeememo.create.recipe.domain.repository.StorageRepository
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