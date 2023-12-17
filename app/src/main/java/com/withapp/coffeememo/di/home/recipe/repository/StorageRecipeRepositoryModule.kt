package com.withapp.coffeememo.di.home.recipe.repository

import com.withapp.coffeememo.home.recipe.data.repository.StorageRepositoryImpl
import com.withapp.coffeememo.home.recipe.domain.repository.StorageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class StorageRecipeRepositoryModule {

    @Binds
    abstract fun bindStorageRecipeRepository(
        storageRepositoryImpl: StorageRepositoryImpl
    ): StorageRepository
}