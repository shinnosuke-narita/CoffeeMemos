package com.withapp.coffeememo.di.favorite.bean.controller


import com.withapp.coffeememo.favorite.bean.presentation.controller.FavoriteBeanController
import com.withapp.coffeememo.favorite.bean.presentation.controller.FavoriteBeanControllerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteBeanControllerModule {

    @Binds
    abstract fun bindFavoriteBeanController(
        favoriteBeanControllerImpl: FavoriteBeanControllerImpl
    ): FavoriteBeanController
}