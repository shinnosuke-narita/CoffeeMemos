package com.example.coffeememos.di.search

import com.example.coffeememos.search.data.repository.SearchRecipeRepositoryImpl
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import com.example.coffeememos.search.domain.repository.SearchRecipeRepository
import com.example.coffeememos.search.presentation.presenter.SearchRecipePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchRecipeRepositoryModule {

    @Binds
    abstract fun bindSearchRecipeRepository(
        SearchRecipeRepositoryImpl: SearchRecipeRepositoryImpl
    ): SearchRecipeRepository
}