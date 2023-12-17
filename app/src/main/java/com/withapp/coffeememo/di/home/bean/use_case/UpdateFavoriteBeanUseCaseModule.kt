package com.withapp.coffeememo.di.home.bean.use_case

import com.withapp.coffeememo.home.bean.domain.interactor.UpdateFavoriteBeanInteractor
import com.withapp.coffeememo.home.bean.domain.use_case.UpdateFavoriteBeanUseCase
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