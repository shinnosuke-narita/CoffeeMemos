package com.example.coffeememos.di.create.recipe.controller

import com.example.coffeememos.create.recipe.presentation.controller.CreateRecipeController
import com.example.coffeememos.create.recipe.presentation.controller.CreateRecipeControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class CreateRecipeControllerModule {

    @Binds
    abstract fun bindCreateRecipeController(
        createRecipeController: CreateRecipeControllerImpl
    ): CreateRecipeController
}