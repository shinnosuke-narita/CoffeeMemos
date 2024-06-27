package com.withapp.coffeememo.di.search.recipe.use_case

import com.withapp.coffeememo.domain.usecase.recipe.updatefavorite.UpdateFavoriteInteractor
import com.withapp.coffeememo.domain.usecase.recipe.updatefavorite.UpdateFavoriteUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UpdateFavoriteUseCaseModule {

    @Binds
    abstract fun bindUpdateFavoriteUseCase(
        updateFavoriteUseCaseImpl : UpdateFavoriteInteractor
    ): UpdateFavoriteUseCase
}