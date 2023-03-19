package com.withapp.coffeememo.di.home.recipe.presenter

import com.withapp.coffeememo.home.recipe.presentation.presenter.HomeRecipePresenter
import com.withapp.coffeememo.home.recipe.presentation.presenter.HomeRecipePresenterImpl
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