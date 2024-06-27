package com.withapp.coffeememo.di.home.bean.use_case

import com.withapp.coffeememo.domain.usecase.bean.updatefavorite.UpdateFavoriteBeanInteractor
import com.withapp.coffeememo.domain.usecase.bean.updatefavorite.UpdateFavoriteBeanUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UpdateFavoriteBeanUseCaseModule {

    @Binds
    abstract fun bindUpdateFavoriteBeanUseCase(
        updateFavoriteUseCaseImpl: UpdateFavoriteBeanInteractor
    ): UpdateFavoriteBeanUseCase
}