package com.withapp.coffeememo.di.favorite.recipe.controller

import com.withapp.coffeememo.favorite.recipe.presentation.controller.FavoriteRecipeController
import com.withapp.coffeememo.favorite.recipe.presentation.controller.FavoriteRecipeControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteRecipeControllerModule {

    @Binds
    abstract fun bindFavoriteRecipeController(
        favoriteRecipeControllerImpl: FavoriteRecipeControllerImpl
    ): FavoriteRecipeController
}