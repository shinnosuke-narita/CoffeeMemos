package com.example.coffeememos.di.search

import com.example.coffeememos.search.presentation.presenter.SearchRecipePresenterImpl
import com.example.coffeememos.search.domain.presenter.SearchRecipePresenter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SearchRecipePresenterModule {

    @Binds
    abstract fun bindSearchRecipePresenter(
        searchRecipePresenterImpl: SearchRecipePresenterImpl
    ): SearchRecipePresenter
}