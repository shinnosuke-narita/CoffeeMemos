package com.withapp.coffeememo.di.favorite.recipe.presenter

import com.withapp.coffeememo.favorite.recipe.presentation.presenter.FavoriteRecipePresenter
import com.withapp.coffeememo.favorite.recipe.presentation.presenter.FavoriteRecipePresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteRecipePresenterModule {

    @Binds
    abstract fun bindFavoriteRecipePresenter(
        favoriteRecipePresenterImpl: FavoriteRecipePresenterImpl
    ): FavoriteRecipePresenter
}