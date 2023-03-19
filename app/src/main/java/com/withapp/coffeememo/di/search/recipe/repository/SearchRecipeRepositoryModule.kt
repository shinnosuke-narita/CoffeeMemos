package com.withapp.coffeememo.di.search.recipe.repository

import com.withapp.coffeememo.search.recipe.data.repository.SearchRecipeDiskRepositoryImpl
import com.withapp.coffeememo.search.recipe.domain.repository.SearchRecipeDiskRepository
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