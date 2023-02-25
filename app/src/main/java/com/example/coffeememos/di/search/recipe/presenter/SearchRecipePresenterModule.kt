package com.example.coffeememos.di.search.recipe.presenter

import com.example.coffeememos.search.recipe.presentation.presenter.SearchRecipePresenterImpl
import com.example.coffeememos.search.recipe.domain.presenter.SearchRecipePresenter
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