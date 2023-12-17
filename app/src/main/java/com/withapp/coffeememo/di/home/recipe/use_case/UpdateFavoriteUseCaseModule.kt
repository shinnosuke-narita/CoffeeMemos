package com.withapp.coffeememo.di.home.recipe.use_case

import com.withapp.coffeememo.home.recipe.domain.interactor.UpdateFavoriteUseCaseInteractor
import com.withapp.coffeememo.home.recipe.domain.use_case.UpdateFavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UpdateFavoriteUseCaseModule {

    @Binds
    abstract fun bindUpdateFavoriteUseCase(
        updateFavoriteImpl: UpdateFavoriteUseCaseInteractor
    ): UpdateFavoriteUseCase
}