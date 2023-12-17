package com.withapp.coffeememo.di.home.recipe.controller

import com.withapp.coffeememo.home.recipe.presentation.controller.HomeRecipeController
import com.withapp.coffeememo.home.recipe.presentation.controller.HomeRecipeControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeRecipeControllerModule {

    @Binds
    abstract fun bindHomeRecipeController(
        homeRecipePresenterImpl: HomeRecipeControllerImpl
    ): HomeRecipeController
}