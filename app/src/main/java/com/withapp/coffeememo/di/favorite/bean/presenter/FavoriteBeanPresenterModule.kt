package com.withapp.coffeememo.di.favorite.bean.presenter


import com.withapp.coffeememo.favorite.bean.presentation.presenter.FavoriteBeanPresenter
import com.withapp.coffeememo.favorite.bean.presentation.presenter.FavoriteBeanPresenterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteBeanPresenterModule {

    @Binds
    abstract fun bindFavoriteBeanPresenter(
        favoriteBeanPresenterImpl: FavoriteBeanPresenterImpl
    ): FavoriteBeanPresenter
}