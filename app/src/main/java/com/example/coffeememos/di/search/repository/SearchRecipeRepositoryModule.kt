package com.example.coffeememos.di.search.repository

import com.example.coffeememos.search.data.repository.SearchRecipeDiskRepositoryImpl
import com.example.coffeememos.search.domain.repository.SearchRecipeDiskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchRecipeRepositoryModule {

    @Binds
    abstract fun bindSearchRecipeRepository(
        SearchRecipeRepositoryImpl: SearchRecipeDiskRepositoryImpl
    ): SearchRecipeDiskRepository
}