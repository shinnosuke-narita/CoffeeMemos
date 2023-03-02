package com.example.coffeememos.di.home.recipe.controller

import com.example.coffeememos.home.recipe.presentation.controller.HomeRecipeController
import com.example.coffeememos.home.recipe.presentation.controller.HomeRecipeControllerImpl
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