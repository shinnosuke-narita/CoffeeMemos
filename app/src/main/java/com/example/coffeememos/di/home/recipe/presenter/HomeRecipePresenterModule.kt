package com.example.coffeememos.di.home.recipe.presenter

import com.example.coffeememos.home.recipe.domain.presenter.HomeRecipePresenter
import com.example.coffeememos.home.recipe.presentation.presenter.HomeRecipePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRecipePresenterModule {

    @Binds
    abstract fun bindHomeRecipePresenter(
        homeRecipePresenterImpl: HomeRecipePresenterImpl
    ): HomeRecipePresenter
}