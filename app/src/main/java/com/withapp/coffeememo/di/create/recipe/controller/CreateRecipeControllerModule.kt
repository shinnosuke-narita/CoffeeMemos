package com.withapp.coffeememo.di.create.recipe.controller

import com.withapp.coffeememo.create.recipe.presentation.controller.CreateRecipeController
import com.withapp.coffeememo.create.recipe.presentation.controller.CreateRecipeControllerImpl
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